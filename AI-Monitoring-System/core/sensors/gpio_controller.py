from abc import ABC, abstractmethod

class GPIOController(ABC):
    @abstractmethod
    def setup_pin(self, pin: int, mode: str):
        pass

    @abstractmethod
    def read_input(self, pin: int) -> bool:
        pass

    @abstractmethod
    def write_output(self, pin: int, state: bool):
        pass

class OrangePiGPIO(GPIOController):
    """适配香橙派AiPro的GPIO实现[11,15](@ref)"""
    def __init__(self):
        import OPi.GPIO as GPIO
        self.gpio = GPIO
        GPIO.setmode(GPIO.BOARD)

    def setup_pin(self, pin, mode):
        self.gpio.setup(pin, self.gpio.IN if mode == "input" else self.gpio.OUT)

    def read_input(self, pin):
        return self.gpio.input(pin)

    def write_output(self, pin, state):
        self.gpio.output(pin, self.gpio.HIGH if state else self.gpio.LOW)