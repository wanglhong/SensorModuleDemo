import websockets
import asyncio
import json
from ..event_bus import EventBus

class WSClient:
    def __init__(self, config):
        self.uri = config['server_url']
        self.device_id = config['device_id']
        self.connection = None
        self.event_bus = EventBus()

    async def connect(self):
        async with websockets.connect(self.uri) as ws:
            self.connection = ws
            await self._authenticate()
            asyncio.create_task(self._receive_handler())

    async def _authenticate(self):
        auth_msg = {"type": "auth", "device_id": self.device_id}
        await self.connection.send(json.dumps(auth_msg))

    async def _receive_handler(self):
        async for message in self.connection:
            event = json.loads(message)
            self.event_bus.publish("ws_command", event)