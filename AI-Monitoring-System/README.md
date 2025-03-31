# AI-Monitoring-System

```text
AI-Surveillance-System/
├── config/                          # 配置模块
│   ├── __init__.py
│   ├── config.yaml                 # 主配置文件
│   └── config_loader.py            # 配置加载器（单例模式）
├── core/                           # 核心业务模块
│   ├── event_bus.py                # 事件总线（线程安全）
│   ├── model_converter/            # 模型转换模块
│   │   ├── yolo_converter.py       # YOLO模型转换器
│   │   └── aipro_optimizer.py      # 香橙派专用优化器
│   ├── video/                      # 视频处理子系统
│   │   ├── capture.py             # 视频监控模块（OpenCV）
│   │   ├── inference.py           # YOLO推理模块
│   │   └── streaming.py            # 视频推送模块（FFmpeg+RTMP）
│   ├── sensors/                    # 传感器子系统
│   │   ├── gpio_controller.py      # GPIO抽象层（适配OrangePi）
│   │   ├── pir_sensor.py           # 人体红外传感器
│   │   └── buzzer.py               # 蜂鸣器控制
│   └── websocket/                  # WebSocket通信子系统
│       ├── ws_client.py            # WebSocket主类
│       ├── event_handler.py        # 事件处理器
│       └── command_parser.py       # 服务端指令解析
├── utils/                          # 工具类库
│   ├── logging.py                  # 统一日志系统
│   └── ffmpeg_wrapper.py           # FFmpeg封装工具
├── MainApplication.py              # 程序入口
└── requirements.txt
```
