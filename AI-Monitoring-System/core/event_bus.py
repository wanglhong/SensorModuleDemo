from concurrent.futures import ThreadPoolExecutor
from typing import Callable, Any
import threading

class EventBus:
    _instance = None
    _lock = threading.Lock()

    def __new__(cls):
        with cls._lock:
            if not cls._instance:
                cls._instance = super().__new__(cls)
                cls._executor = ThreadPoolExecutor(max_workers=8)
                cls._subscribers = {}
            return cls._instance

    def subscribe(self, event_type: str, callback: Callable[[Any], None]):
        if event_type not in self._subscribers:
            self._subscribers[event_type] = []
        self._subscribers[event_type].append(callback)

    def publish(self, event_type: str, data: Any):
        if event_type in self._subscribers:
            for callback in self._subscribers[event_type]:
                self._executor.submit(callback, data)