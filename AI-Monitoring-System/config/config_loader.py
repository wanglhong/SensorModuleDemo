import yaml
from threading import Lock

class ConfigLoader:
    _instance = None
    _lock = Lock()

    def __new__(cls):
        with cls._lock:
            if not cls._instance:
                cls._instance = super().__new__(cls)
                cls._config = cls._load_config()
            return cls._instance

    @classmethod
    def _load_config(cls):
        with open('config/config.yaml') as f:
            return yaml.safe_load(f)

    @property
    def gpio_config(self):
        return self._config['gpio']

    @property
    def websocket_config(self):
        return self._config['websocket']

    # 其他配置项访问器...