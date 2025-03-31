from config.config_loader import ConfigLoader
from core.websocket.ws_client import WSClient
from core.video.capture import VideoCapture
from core.video.inference import YOLOInference
from core.sensors.pir_sensor import PIRSensor
from core.sensors.buzzer import BuzzerController
from core.event_bus import EventBus

class MainApp:
    def __init__(self):
        self.config = ConfigLoader()
        self.event_bus = EventBus()

        # 初始化模块
        self.ws_client = WSClient(self.config.websocket)
        self.video_cap = VideoCapture(0)
        self.yolo = YOLOInference(self.config.model_path)
        self.pir = PIRSensor(OrangePiGPIO(), self.config.gpio.pir_pin)
        self.buzzer = BuzzerController(self.config.gpio.buzzer_pin)

    def start(self):
        """启动所有服务[5,9](@ref)"""
        # 启动WebSocket
        asyncio.run(self.ws_client.connect())

        # 启动视频处理线程
        video_thread = threading.Thread(target=self.process_video)
        video_thread.start()

        # 启动传感器监控
        pir_thread = threading.Thread(target=self.pir.start_monitoring)
        pir_thread.start()

    def process_video(self):
        """视频处理流水线"""
        for frame in self.video_cap.stream():
            processed = self.yolo.process_frame(frame)
            if self.yolo.is_abnormal():
                self.buzzer.alert()
                self.event_bus.publish("abnormal_detected", results)