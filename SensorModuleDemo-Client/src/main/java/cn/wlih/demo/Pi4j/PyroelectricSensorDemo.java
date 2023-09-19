package cn.wlih.demo.Pi4j;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.*;
import com.pi4j.util.Console;

import com.pi4j.plugin.mock.provider.gpio.digital.MockDigitalInput;
import com.pi4j.plugin.mock.provider.gpio.digital.MockDigitalInputProvider;

import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 *
 * @author 王立宏
 * 描述: 热释电传感器（人体红外传感器：http://www.raspi.cc/read-66-1.html）
 * 有人时输出高电平 --- 无人时输出低电平
 * path: SensorModuleDemo-cn.wlih.demo.Pi4j-PyroelectricSensorDemo
 * date: 2023/9/1 21:39
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
//                .address(PIN_PS)
                //下拉
                .pull(PullResistance.PULL_DOWN)
                .provider("raspberrypi-digital-input")
                .build();

        // 从 Pi4J 上下文中获取数字输入 IO 提供程序
        DigitalInputProvider digitalInputProvider = pi4j.provider("raspberrypi-digital-input");

        DigitalInput input = digitalInputProvider.create(config);

        //打印注册表
        PrintInfo.printRegistry(console, pi4j);

        //监听输入 ----> 检测GPIO20的输入状态
//        input.addListener(event -> {
//            Integer count = (Integer) event.source().metadata().get("count").value();
//            console.println(event + " <===> " + count);
//        });
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

//        console.waitForExit();

        pi4j.shutdown();
    }

    public void demo02() {
        // create Pi4J console wrapper/helper
        // (This is a utility class to abstract some of the boilerplate stdin/stdout code)
        final var console = new Console();

        // print program title/header
        console.title("<-- The Pi4J Project -->", "Basic Digital Input Example With Mock Provider");

        // allow for user to exit program using CTRL-C
        console.promptForExit();

        // Initialize Pi4J with an auto context
        // An auto context includes AUTO-DETECT BINDINGS enabled
        // which will load all detected Pi4J extension libraries
        // (Platforms and Providers) in the class path
        var pi4j = Pi4J.newAutoContext();

        // get the Mock Digital Input provider by ID
        console.println("尝试从 Pi4J 获取模拟数字输入提供程序");
        var provider = pi4j.providers().get(MockDigitalInputProvider.ID, MockDigitalInputProvider.class);

        // display acquired provider
        console.println("--> 收购的提供商: ");
        console.print("--> ");
        console.print(provider);
        console.println();

        // create a digital input instance using the default digital input provider
        console.println("尝试创建模拟数字输入实例");
        MockDigitalInput input = provider.create(PIN_PS);

        // display created instance
        console.print("--> 已创建 IO 实例: ");
        console.print(input);
        console.println();

        // setup a digital output listener to listen for any state changes on the digital input
        input.addListener((DigitalStateChangeListener) event -> {
            console.print("数字输入 [");
            console.print(event.source());
            console.print("] 状态更改: ");
            console.println(event.state());
        });

        // lets read the digital output state
        console.print("当前数字输入状态为 [");
        console.println(input.state() + "]");

        // change mock state of mock digital input so we can see some events firing
        input.mockState(DigitalState.HIGH)
                .mockState(DigitalState.LOW)
                .mockState(DigitalState.HIGH)
                .mockState(DigitalState.LOW);

        // shutdown Pi4J
        console.println("尝试关机终止此程序");
        pi4j.shutdown();
    }

}
