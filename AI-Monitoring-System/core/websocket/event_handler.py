from core.event_bus import EventBus

class WebsocketEventHandler:
    def __init__(self, ws_client):
        self.ws = ws_client
        self.event_bus = EventBus()

        # 注册事件监听
        self.event_bus.subscribe("abnormal_detected", self.handle_abnormal)
        self.event_bus.subscribe("human_detected", self.handle_human)

    def handle_abnormal(self, data):
        """处理YOLO异常检测事件[1,7](@ref)"""
        message = {
            'event': 'object_detected',
            'data': data
        }
        self.ws.send(json.dumps(message))

    def handle_human(self, data):
        """处理人体检测事件[17](@ref)"""
        message = {
            'event': 'human_detected',
            'data': data
        }
        self.ws.send(json.dumps(message))