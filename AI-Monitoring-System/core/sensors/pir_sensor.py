from core.event_bus import EventBus

class PIRSensor:
    def __init__(self, gpio_controller, pin):
        self.gpio = gpio_controller
        self.pin = pin
        self.gpio.setup_pin(pin, "input")
        self.event_bus = EventBus()

    def start_monitoring(self):
        """人体红外检测循环[17,19](@ref)"""
        while True:
            if self.gpio.read_input(self.pin):
                self.event_bus.publish("human_detected", {
                    'timestamp': time.time(),
                    'location': 'default'
                })
            time.sleep(0.1)