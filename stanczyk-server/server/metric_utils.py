import copy
from collections import OrderedDict
from datetime import datetime
from uuid import UUID, uuid4

import psutil
from google.protobuf.json_format import MessageToJson
from tzlocal import get_localzone

from generated.stanczyk_pb2 import KnowledgeBatch


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
    return [{**MessageToJson(point.deviceExecutorMetadata), **MessageToJson(point.taskMetadata),
             **{"problemSize": point.problemSize}} for point in data]


def cloud_knowledge(metrics):
    pass  # todo - translate internally stored knowledge to CloudKnowledge dto


def devices_knowledge(metrics):
    pass  # todo - translate internally stored knowledge to DevicesKnowledge dto


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
        pass  # todo - see dto_to_knowledge etc


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
