import subprocess

class FFmpegWrapper:
    @staticmethod
    def convert_format(input_path, output_path):
        """视频格式转换[31](@ref)"""
        cmd = f"ffmpeg -i {input_path} -c:v libx264 -preset fast {output_path}"
        subprocess.run(cmd, shell=True, check=True)

    @staticmethod
    def extract_audio(video_path, audio_path):
        """提取音频流"""
        cmd = f"ffmpeg -i {video_path} -vn -acodec copy {audio_path}"
        subprocess.run(cmd, shell=True, check=True)