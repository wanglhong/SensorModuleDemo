package cn.wlih.demo.Pi4j;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.*;
import com.pi4j.util.Console;

/**
 * 描述: 热释电传感器（人体红外传感器：http://www.raspi.cc/read-66-1.html）
 * 有人时输出高电平 --- 无人时输出低电平
 * @author 王立宏
 * @date 2023/9/1 21:39
 * @path SensorModuleDemo-cn.wlih.demo.Pi4j-PyroelectricSensorDemo
 */
public class PyroelectricSensorDemo {

    private static final int PIN_PS = 20; // PIN 38 = BCM 20
    private static Console console = new Console();

    public void psDemo() throws InterruptedException {
        console.title("<-- The Pi4J Project -->", "热释电传感器 Demo");
        // 允许用户使用Ctrl-C退出程序
        console.promptForExit();
        //初始化pi4j
        Context pi4j = Pi4J.newAutoContext();
        //打印注册表
        PrintInfo.printRegistry(console, pi4j);

//        Properties properties = new Properties();
//        properties.put("id", "PS");
//        properties.put("address", PIN_PS);
//        properties.put("pull", "UP"); //上拉PullResistance.PULL_UP
//        properties.put("name", "PS Input");

        DigitalInputConfig config = DigitalInput.newConfigBuilder(pi4j)
                .id("my-digital-input")
                .name("my-name")
                .address(PIN_PS)
                //下拉
                .pull(PullResistance.PULL_DOWN)
                .provider("pigpio-digital-input")
                .build();

        // 从 Pi4J 上下文中获取数字输入 IO 提供程序
        DigitalInputProvider digitalInputProvider = pi4j.provider("pigpio-digital-input");

        DigitalInput input = digitalInputProvider.create(config);

        //打印注册表
        PrintInfo.printRegistry(console, pi4j);

        //监听输入 ----> 检测GPIO20的输入状态
        input.addListener(event -> {
            Integer count = (Integer) event.source().metadata().get("count").value();
            console.println(event + " <===> " + count);
        });
//        for (int i = 0; i < 5; i++) {
//            console.println("准备开始监听！");
//            input.addListener(e -> {
//                console.println("进入了监听方法块！");
//                if (e.state() == DigitalState.HIGH) {
//                    console.println("高电平输入 ----> 有人");
//                } else {
//                    console.println("低电平输入 ----> 无人");
//                }
//            });
//            Thread.sleep(500);
//        }

//        for (int i = 0; i < 10; i++) {
//            console.println("准备开始监听！");
//            if (input.state() == DigitalState.HIGH) {
//                console.println("高电平输入 ----> 有人");
//            } else {
//                console.println("低电平输入 ----> 无人");
//            }
//            Thread.sleep(500);
//        }

        console.waitForExit();

        pi4j.shutdown();
    }

}
