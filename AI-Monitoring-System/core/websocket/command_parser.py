import json

class CommandParser:
    @staticmethod
    def parse_command(raw_data):
        """解析服务端指令[5,9](@ref)"""
        try:
            cmd = json.loads(raw_data)
            return {
                'type': cmd.get('action'),
                'params': cmd.get('params', {})
            }
        except Exception as e:
            print(f"指令解析失败: {e}")
            return None