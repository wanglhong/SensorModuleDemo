package cn.wlih.demo.Pi4j;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalOutputConfigBuilder;
import com.pi4j.io.gpio.digital.DigitalState;
import com.pi4j.platform.Platforms;
import com.pi4j.util.Console;

/**
 * Created by IntelliJ IDEA.
 *
 * @author 王立宏
 * 描述: Hello Pi4j
 * path: SensorModuleDemo-cn.wlih.demo.Pi4j-Pi4jDemo001
 * date: 2023/9/1 0:11
 */
public class Pi4jDemo001 {

    //嗡鸣器（H/HA）IO口 ---> 低电平触发
    private static final int PIN_HA = 17; // PIN 11 = BCM 17
    private static Console console = new Console();

    public void demo01() throws InterruptedException {
        console.title("<-- The Pi4J Project -->", "最小示例项目");
        //初始化Pi4j
        Context pi4j = Pi4J.newAutoContext();

        //输出 Pi4J 上下文信息
        PrintInfo.printLoadedPlatforms(console, pi4j);
        PrintInfo.printDefaultPlatform(console, pi4j);
        PrintInfo.printProviders(console, pi4j);

        console.println("开始进行嗡鸣器测试！");
        DigitalOutputConfigBuilder haConfig = DigitalOutput.newConfigBuilder(pi4j)
                .id("HA")
                .name("HA Beep")
                .address(PIN_HA)
                .shutdown(DigitalState.HIGH)
                .initial(DigitalState.HIGH)
                //供应商，不是随便写的pigpio-digital-output
                .provider("raspberrypi-digital-output");
        DigitalOutput ha = pi4j.create(haConfig);
        //打印注册表
        PrintInfo.printRegistry(console, pi4j);
        for (int pressCount = 0; pressCount < 10; pressCount++) {
            if (ha.equals(DigitalState.LOW)) {
                ha.high();
                console.println("HA high ---> 关闭");
                Thread.sleep(1000);
            } else {
                ha.low();
                console.println("HA low ---> 触发");
                Thread.sleep(500);
            }
        }

        //关闭pi4j
        pi4j.shutdown();
    }

}
