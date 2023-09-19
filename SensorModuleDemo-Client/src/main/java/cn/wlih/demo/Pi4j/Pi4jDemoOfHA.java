package cn.wlih.demo.Pi4j;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalOutputConfigBuilder;
import com.pi4j.io.gpio.digital.DigitalState;
import com.pi4j.util.Console;

/**
 * Created by IntelliJ IDEA.
 *
 * @author 王立宏
 * 描述: HA有源嗡鸣器测试
 * path: SensorModuleDemo-cn.wlih.demo.Pi4j-Pi4jDemoOfHA
 * date: 2023/9/1 0:11
 */
public class Pi4jDemoOfHA {

    //嗡鸣器（H/HA）IO口 ---> 低电平触发
    private static final int PIN_HA = 17; // PIN 11 = BCM 17
    private static Console console = new Console();

    public void demo01() throws InterruptedException {
        console.title("<-- The Pi4J Project -->", "有源嗡鸣器 Demo");
        //初始化Pi4j
        Context pi4j = Pi4J.newAutoContext();

        //输出 Pi4J 上下文信息
        console.println("----------------> 输出 Pi4J 上下文信息 <---------------");
        PrintInfo.printLoadedPlatforms(console, pi4j);
        PrintInfo.printDefaultPlatform(console, pi4j);
        PrintInfo.printProviders(console, pi4j);

        console.println("开始进行嗡鸣器测试！");
        DigitalOutputConfigBuilder haConfig = DigitalOutput.newConfigBuilder(pi4j)
                .id("HA")
                .name("HA Beep")
                .address(PIN_HA)
                .shutdown(DigitalState.LOW)
                .initial(DigitalState.LOW)
                //供应商，不是随便写的pigpio-digital-output
                .provider("raspberrypi-digital-output");
        DigitalOutput ha = pi4j.create(haConfig);

        //打印注册表
        PrintInfo.printRegistry(console, pi4j);

        for (int pressCount = 0; pressCount < 10; pressCount++) {
            if (ha.equals(DigitalState.LOW)) {
                ha.high();
                console.println("HA High ---> 触发");
                Thread.sleep(500);
            } else {
                ha.low();
                console.println("HA Low  ---> 关闭");
                Thread.sleep(1000);
            }
        }

        //关闭pi4j
        pi4j.shutdown();
    }

}
