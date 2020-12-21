import copy
from collections import OrderedDict
from datetime import datetime
from uuid import UUID

import psutil
from tzlocal import get_localzone


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


class MetricCollector:
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

    def get_recent_metrics(self):
        metrics = dict(copy.deepcopy(self.final_store))
        return {k: v for (k, v) in metrics.items() if isinstance(k, UUID)}


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
