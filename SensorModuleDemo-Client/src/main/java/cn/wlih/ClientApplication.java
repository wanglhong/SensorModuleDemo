package cn.wlih;

import cn.wlih.component.ComponentFactory;
import cn.wlih.appPublic.enums.ProgramInvocationPattern;
import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.library.pigpio.PiGpio;
import com.pi4j.plugin.linuxfs.provider.i2c.LinuxFsI2CProvider;
import com.pi4j.plugin.pigpio.provider.gpio.digital.PiGpioDigitalInputProvider;
import com.pi4j.plugin.pigpio.provider.gpio.digital.PiGpioDigitalOutputProvider;
import com.pi4j.plugin.pigpio.provider.pwm.PiGpioPwmProvider;
import com.pi4j.plugin.pigpio.provider.serial.PiGpioSerialProvider;
import com.pi4j.plugin.pigpio.provider.spi.PiGpioSpiProvider;
import com.pi4j.plugin.raspberrypi.platform.RaspberryPiPlatform;
import lombok.extern.slf4j.Slf4j;

/**
 * 描述: 客户端启动类
 * @author 王立宏
 * @date 2023/8/25 9:36
 * @path SensorModuleDemo-cn.wlih-ClientApplication
 */
@Slf4j
public class ClientApplication {

    private static final ComponentFactory componentFactory = new ComponentFactory();
    private Context pi4jContext;

    /**
     * 程序入口
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        new ClientApplication();
    }

    public ClientApplication() {
        // 初始化 Pi4j 上下文
        final PiGpio piGpio = PiGpio.newNativeInstance();
        pi4jContext = Pi4J.newContextBuilder()
                .noAutoDetect()
                .add(new RaspberryPiPlatform() {
                    @Override
                    protected String[] getProviders() {
                        return new String[]{};
                    }
                })
                .add(PiGpioDigitalInputProvider.newInstance(piGpio),
                        PiGpioDigitalOutputProvider.newInstance(piGpio) ,
                        PiGpioPwmProvider.newInstance(piGpio),
                        PiGpioSerialProvider.newInstance(piGpio),
                        PiGpioSpiProvider.newInstance(piGpio),
                        LinuxFsI2CProvider.newInstance()
                )
                .build();
        all : while (true) {
            // TODO　查询 redis 中的登录信息 判断是否需要继续运行
            switch (ProgramInvocationPattern.valueOf("CLIENT")) {
                case EXIT -> {
                    log.warn("程序退出，并关闭计算机！");
                    break all;
                } case STOP -> {
                    log.info("程序停止，不进行信息收集。");
                } case START -> {
                    log.info("程序启动");
                } case LOOP -> {
                    log.info("程序循环");
                }
            }
            // 查询程序调用模式
        }
        // 程序退出时释放 pi4j 上下文资源
        pi4jContext.shutdown();
        // 关闭计算机
    }

}
