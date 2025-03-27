import asyncio
import json
import websockets
import cv2
import subprocess
import numpy as np
from threading import Thread, Lock
import traceback
import time

class WebSocketClient:
    def __init__(self, client_id, server_url="ws://100.114.118.116:8080"):
        self.client_id = client_id
        self.uri = f"{server_url}/ws/{client_id}"
        self.websocket = None
        self.recording = False
        self.stream_process = None
        self.cap = None
        self.camera_lock = Lock()
        self.retry_count = 0
        self.max_retries = 5
        self.base_delay = 1  # 指数退避基数
        self.last_pong = time.time()  # 心跳检测

    async def connect(self):
        """增强型连接方法"""
        while self.retry_count < self.max_retries:
            try:
                self.websocket = await websockets.connect(
                    self.uri,
                    ping_interval=20,
                    ping_timeout=60,
                    close_timeout=10
                )
                # 连接状态双重验证
                if self.websocket.open and self.websocket.state == 'OPEN':
                    print(f"[{time.ctime()}] 连接成功，状态：{self.websocket.state.name}")
                    await self._send_handshake()  # 发送自定义握手协议
                    self.retry_count = 0
                    return
                else:
                    raise ConnectionError("协议升级未完成")
            except Exception as e:
                delay = self.base_delay * 2 ​** self.retry_count
                print(f"连接失败: {str(e)}，{delay}秒后重试...")
                await asyncio.sleep(delay)
                self.retry_count += 1
        raise ConnectionError("超过最大重试次数")

    async def _send_handshake(self):
        """自定义握手协议(参考网页3的网络配置)"""
        await self.websocket.send(json.dumps({
            "event": "auth",
            "data": {
                "client_type": "edge_device",
                "hardware": "Ascend310",
                "ip": self._get_local_ip()  # 获取本地IP用于服务端验证
            }
        }))

    def _get_local_ip(self):
        """获取本地IP(适用于香橙派网络配置)"""
        import socket
        s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        try:
            s.connect(('8.8.8.8', 80))
            ip = s.getsockname()[0]
        finally:
            s.close()
        return ip

    async def receive_messages(self):
        """带心跳检测的消息接收"""
        try:
            async for message in self.websocket:
                self.last_pong = time.time()  # 重置心跳计时
                self._handle_message(message)
        except websockets.exceptions.ConnectionClosed as e:
            print(f"连接异常关闭: {e.code}->{e.reason}")
            await self._reconnect()
        except Exception as e:
            print(f"接收异常: {str(e)}")
            traceback.print_exc()

    async def _reconnect(self):
        """智能重连策略(参考网页1的硬件重连机制)"""
        print("启动重连流程...")
        if self.websocket:
            await self.websocket.close()
        await self.connect()
        await self.resume_stream()  # 恢复视频流

    async def resume_stream(self):
        """断线恢复机制"""
        if self.recording:
            print("尝试恢复视频推流...")
            self._stop_recording()
            await asyncio.sleep(1)
            self._start_recording()

    def _handle_message(self, message):
        """增强型消息处理器"""
        try:
            if isinstance(message, bytes):
                self._handle_binary(message)
                return

            data = json.loads(message)
            if not all(key in data for key in ('event', 'data')):
                raise ValueError("无效消息格式")

            event = data['event']
            params = data['data']

            print(f"收到事件 [{event}]：{params}")

            if event == "viewMonitor":
                self._handle_stream_control(params)
            elif event == "heartbeat":
                self._handle_heartbeat(params)

        except Exception as e:
            print(f"消息处理失败: {str(e)}")
            traceback.print_exc()

    def _handle_stream_control(self, params):
        """视频流控制处理器(参考网页4的流媒体控制)"""
        action = params.get("action")
        if action == "start" and not self.recording:
            self.recording = True
            Thread(target=self._video_pipeline, daemon=True).start()
        elif action == "stop" and self.recording:
            self.recording = False

    def _video_pipeline(self):
        """增强型视频处理流水线"""
        try:
            with self.camera_lock:  # 硬件资源独占访问
                self._init_camera()
                self._init_ffmpeg()

                while self.recording:
                    self._process_frame()
                    self._check_stream_health()

        except Exception as e:
            print(f"视频流水线异常: {str(e)}")
            traceback.print_exc()
        finally:
            self._release_resources()

    def _init_camera(self):
        """摄像头初始化(参考网页2的摄像头配置)"""
        self.cap = cv2.VideoCapture(0)
        self.cap.set(cv2.CAP_PROP_FRAME_WIDTH, 640)
        self.cap.set(cv2.CAP_PROP_FRAME_HEIGHT, 480)
        self.cap.set(cv2.CAP_PROP_FPS, 25)
        if not self.cap.isOpened():
            raise RuntimeError("摄像头初始化失败")

    def _init_ffmpeg(self):
        """FFmpeg进程初始化(参考网页6的推流配置)"""
        ffmpeg_cmd = [
            'ffmpeg',
            '-y', '-f', 'rawvideo',
            '-vcodec', 'rawvideo',
            '-pix_fmt', 'bgr24',
            '-s', '640x480', '-r', '25', '-i', '-',
            '-c:v', 'h264_omx',  # 启用硬件编码
            '-preset', 'ultrafast',
            '-tune', 'zerolatency',
            '-f', 'flv', 'rtmp://wlih.cn:1935/stream/1003'
        ]
        self.stream_process = subprocess.Popen(
            ffmpeg_cmd,
            stdin=subprocess.PIPE,
            stderr=subprocess.PIPE
        )

    def _process_frame(self):
        """帧处理流水线"""
        ret, frame = self.cap.read()
        if not ret:
            raise RuntimeError("视频帧读取失败")

        # 昇腾硬件预处理(参考网页5的AI加速)
        processed_frame = cv2.cvtColor(frame, cv2.COLOR_BGR2YUV_I420)
        self.stream_process.stdin.write(processed_frame.tobytes())

    def _check_stream_health(self):
        """流健康检查"""
        if self.stream_process.poll() is not None:
            raise RuntimeError("FFmpeg进程异常退出")

    def _release_resources(self):
        """资源释放"""
        with self.camera_lock:
            if self.cap and self.cap.isOpened():
                self.cap.release()
            if self.stream_process:
                self.stream_process.stdin.close()
                self.stream_process.wait()
        cv2.destroyAllWindows()

    async def run(self):
        """主运行循环"""
        await self.connect()
        await asyncio.gather(
            self.receive_messages(),
            self._heartbeat_checker(),
            self._status_monitor()
        )

    async def _heartbeat_checker(self):
        """心跳检测器"""
        while True:
            if time.time() - self.last_pong > 30:  # 30秒无心跳
                print("心跳超时，触发重连")
                await self._reconnect()
            await asyncio.sleep(5)

    async def _status_monitor(self):
        """系统状态监控"""
        while True:
            status = {
                "cpu_load": self._get_cpu_usage(),
                "camera_status": "active" if self.recording else "idle",
                "stream_status": "running" if self.stream_process else "stopped"
            }
            await self.send_event("deviceStatus", status)
            await asyncio.sleep(10)

    def _get_cpu_usage(self):
        """获取CPU使用率(香橙派专用)"""
        with open('/proc/stat') as f:
            fields = [float(column) for column in f.readline().strip().split()[1:]]
        idle = fields[3]
        total = sum(fields)
        return (total - idle) / total if total != 0 else 0

if __name__ == "__main__":
    client = WebSocketClient(client_id="orange_pi_001")
    try:
        asyncio.get_event_loop().run_until_complete(client.run())
    except KeyboardInterrupt:
        print("程序已安全终止")