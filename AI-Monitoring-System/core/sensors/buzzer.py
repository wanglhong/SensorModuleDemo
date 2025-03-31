from machine import PWM, Pin

class BuzzerController:
    def __init__(self, pin):
        self.pwm = PWM(Pin(pin))
        self.pwm.duty(0)

    def alert(self, duration=1, frequency=2000):
        """触发蜂鸣警报[12,13](@ref)"""
        self.pwm.freq(frequency)
        self.pwm.duty(512)  # 50%占空比
        time.sleep(duration)
        self.pwm.duty(0)