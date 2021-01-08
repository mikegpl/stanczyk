import copy
from collections import OrderedDict
from datetime import datetime
from uuid import UUID, uuid4

import psutil
from google.protobuf.json_format import MessageToDict
from tzlocal import get_localzone

from generated.stanczyk_pb2 import KnowledgeBatch, CloudKnowledge, CloudExecutionMetadata, CloudExecutorMetadata, \
    TaskMetadata, DevicesKnowledge, DeviceExecutionMetadata, DeviceExecutorMetadata


def get_cpu_load_history():
    """
    Returns load avg in last [1 min, 5 min, 15 min]
    """
    cpu_count = psutil.cpu_count()
    load = psutil.getloadavg()
    normalized_load = [ld / cpu_count * 100 for ld in load]
    return normalized_load


def get_available_ram() -> int:
    return psutil.virtual_memory().available


def get_day_and_hour():
    dtime = datetime.now(tz=get_localzone())
    return dtime.weekday(), dtime.hour


def knowledge_to_dto(devices_metrics, server_metrics):
    return KnowledgeBatch(cloudKnowledge=cloud_knowledge(server_metrics),
                          devicesKnowledge=devices_knowledge(devices_metrics))


def devices_dto_to_knowledge(knowledge_dto):
    data = list(knowledge_dto.data)
    return [{**MessageToDict(point.deviceExecutorMetadata), **MessageToDict(point.taskMetadata),
             **{"executionTimeMs": point.executionTimeMs}} for point in data]


def devices_knowledge(device_metrics):
    devices_data = [DeviceExecutionMetadata(
        deviceExecutorMetadata=DeviceExecutorMetadata(
            cpuRating=metrics["cpuRating"],
            networkRating=metrics["networkRating"],
            memoryAvailable=metrics["memoryAvailable"],
            totalMemory=metrics["totalMemory"],
            sdkScore=metrics["sdkScore"],
            batteryPercentage=metrics["batteryPercentage"]
        ),
        taskMetadata=TaskMetadata(
            problemSize=metrics["problemSize"]
        ),
        executionTimeMs=metrics["executionTimeMs"]
    ) for metrics in device_metrics]
    return DevicesKnowledge(data=devices_data)


def cloud_knowledge(cloud_metrics):
    cloud_data = [CloudExecutionMetadata(
        cloudExecutorMetadata=CloudExecutorMetadata(
            cpuLoad=metrics["cpu"],
            ramAvailable=metrics["ram"],
            dayOfWeek=metrics["dayOfWeek"],
            hourOfDay=metrics["hourOfDay"]
        ),
        taskMetadata=TaskMetadata(
            problemSize=metrics["size"]
        ),
        executionTimeMs=metrics["runtime"] * 1000
    ) for metrics in cloud_metrics]
    return CloudKnowledge(data=cloud_data)


class ServerMetricCollector:
    def __init__(self, maxsize=1000):
        self.initial_store = LRUCache(maxsize=maxsize)
        self.final_store = LRUCache(maxsize=maxsize)

    def collect_initial(self, uuid):
        cpu = get_cpu_load_history()
        ram = get_available_ram()
        day_of_week, hour = get_day_and_hour()
        start = datetime.now().timestamp()
        metrics = {
            "cpu": cpu,
            "ram": ram,
            "dayOfWeek": day_of_week,
            "hourOfDay": hour,
            "start": start
        }
        self.initial_store[uuid] = metrics

    def insert(self, uuid, key, value):
        if uuid in self.initial_store:
            self.initial_store[uuid][key] = value

    def collect_final(self, uuid):
        start_metrics = self.initial_store[uuid]
        del self.initial_store[uuid]
        runtime = datetime.now().timestamp() - start_metrics["start"]
        del start_metrics["start"]
        start_metrics["runtime"] = runtime
        self.final_store[uuid] = start_metrics


class DevicesMetricCollector:
    def __init__(self, maxsize=1000):
        self.store = LRUCache(maxsize=maxsize)

    def insert(self, metrics, uuid=uuid4()):
        self.store[uuid] = metrics

    def insert_many(self, metrics_list):
        for metrics in metrics_list:
            self.insert(metrics)


class StanczykMetricCollector:
    def __init__(self, server_size=1000, devices_size=1000):
        self.devices = DevicesMetricCollector(maxsize=devices_size)
        self.server = ServerMetricCollector(maxsize=server_size)

    @staticmethod
    def _extract_metrics(store):
        metrics = dict(copy.deepcopy(store))
        return {k: v for (k, v) in metrics.items() if isinstance(k, UUID)}

    def get_recent_metrics(self):
        return self._extract_metrics(self.devices), self._extract_metrics(self.server)


class LRUCache(OrderedDict):
    'Limit size, evicting the least recently looked-up key when full'

    def __init__(self, maxsize=128, /, *args, **kwds):
        self.maxsize = maxsize
        super().__init__(*args, **kwds)

    def __getitem__(self, key):
        value = super().__getitem__(key)
        self.move_to_end(key)
        return value

    def __setitem__(self, key, value):
        if key in self:
            self.move_to_end(key)
        super().__setitem__(key, value)
        if len(self) > self.maxsize:
            oldest = next(iter(self))
            del self[oldest]
