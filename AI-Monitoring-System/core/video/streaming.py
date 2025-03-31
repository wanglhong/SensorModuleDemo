import cv2
import subprocess
from core.event_bus import EventBus

class VideoStreamer:
    def __init__(self, rtmp_url):
        self.rtmp_url = rtmp_url
        self.process = None
        self.event_bus = EventBus()

    def start_streaming(self, frame):
        """启动FFmpeg推流进程[31](@ref)"""
        command = [
            'ffmpeg',
            '-y',
            '-f', 'rawvideo',
            '-vcodec','rawvideo',
            '-pix_fmt', 'bgr24',
            '-s', '640x480',
            '-r', '25',
            '-i', '-',
            '-c:v', 'libx264',
            '-preset', 'ultrafast',
            '-f', 'flv',
            self.rtmp_url
        ]
        self.process = subprocess.Popen(command, stdin=subprocess.PIPE)

    def push_frame(self, frame):
        """推送视频帧"""
        if self.process:
            self.process.stdin.write(frame.tobytes())

    def stop_streaming(self):
        if self.process:
            self.process.stdin.close()
            self.process.wait()