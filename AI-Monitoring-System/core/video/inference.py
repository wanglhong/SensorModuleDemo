import torch
from ..event_bus import EventBus

class YOLOInference:
    def __init__(self, model_path):
        self.model = torch.hub.load('ultralytics/yolov5', 'custom', path=model_path)
        self.event_bus = EventBus()

    def process_frame(self, frame):
        results = self.model(frame)
        if self._is_abnormal(results):
            self.event_bus.publish("abnormal_detected", results)
        return results.render()[0]

    def _is_abnormal(self, results):
        # 异常检测逻辑
        return any(obj in ['person', 'car'] for obj in results.names.values())