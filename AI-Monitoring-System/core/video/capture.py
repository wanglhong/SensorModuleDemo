import cv2
from ..event_bus import EventBus

class VideoCapture:
    def __init__(self, config):
        self.cap = cv2.VideoCapture(config['camera_index'])
        self.event_bus = EventBus()

    def start_streaming(self):
        while True:
            ret, frame = self.cap.read()
            if ret:
                self.event_bus.publish("video_frame", frame)

    def get_frame(self):
        return self.cap.read()[1]