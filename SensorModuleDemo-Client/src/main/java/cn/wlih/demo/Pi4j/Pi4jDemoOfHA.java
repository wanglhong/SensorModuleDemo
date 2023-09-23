package cn.wlih.demo.Pi4j;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalOutputConfigBuilder;
import com.pi4j.io.gpio.digital.DigitalState;
import com.pi4j.util.Console;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 *
 * @author 王立宏
 * 描述: HA有源嗡鸣器测试
 * path: SensorModuleDemo-cn.wlih.demo.Pi4j-Pi4jDemoOfHA
 * date: 2023/9/1 0:11
 */
@Slf4j
public class Pi4jDemoOfHA {

    //嗡鸣器（H/HA）IO口 ---> 高电平触发
    private static final int PIN_HA = 4;
    private static Console console = new Console();

    public void demo01() throws InterruptedException {
        console.title("<-- The Pi4J Project -->", "Digital Output Test (BCM-18)");
        //初始化Pi4j
        Context pi4j = Pi4J.newAutoContext();

        DigitalOutputConfigBuilder haConfig = DigitalOutput.newConfigBuilder(pi4j)
                .id("HA")
                .name("HA Beep")
                .address(PIN_HA)
                .shutdown(DigitalState.LOW)
                .initial(DigitalState.LOW)
                //供应商（详情请查看Pi4j文档关于供应商的模块的介绍）
                .provider("pigpio-digital-output");
        DigitalOutput ha = pi4j.create(haConfig);

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

    public void demo02() throws InterruptedException {
        log.info("========================》 最新版本Pi4j数字输出相关调用Demo 《========================");
        // 使用自动上下文初始化Pi4J
        // 自动上下文包括启用的AUTO-DETECT绑定
        // 它将加载所有检测到的Pi4J扩展库
        // 类路径中的（平台和提供商）
        Context pi4j = Pi4J.newAutoContext();
        // 配置数字输出
        DigitalOutputConfigBuilder haConfig = DigitalOutput.newConfigBuilder(pi4j)
                .id("HA")
                .name("HA Beep")
                .address(PIN_HA)
                .shutdown(DigitalState.LOW)
                .initial(DigitalState.LOW)
                //供应商（详情请查看Pi4j文档关于供应商的模块的介绍）
                .provider("pigpio-digital-output");
        DigitalOutput digitalOutput = pi4j.create(haConfig);

        // 设置数字输出侦听器以侦听数字输出上的任何状态变化
        digitalOutput.addListener(out -> {
            log.warn(out.toString());
        });

        // 更改 4 次数字输出的状态
        log.info("========> 更改 4 次数字输出的状态 <========");
        digitalOutput.state(DigitalState.HIGH)
                .state(DigitalState.LOW)
                .state(DigitalState.HIGH)
                .state(DigitalState.LOW);

        // 切换 3 次数字输出状态
        System.out.println();
        log.info("========> 切换 3 次数字输出状态 <========");
        digitalOutput.toggle()
                .toggle()
                .toggle();

        // 另一种设置输出状态的友好方法
        System.out.println();
        log.info("========> 另一种设置输出状态的友好方法 <========");
        digitalOutput.low().high();

        // 读取数字输出状态
        System.out.println();
        log.info("========> 读取数字输出状态 <========");
        log.info("电流数字输出 [" + digitalOutput + "] 状态为 [" + digitalOutput.state() + "]");

        // 脉冲到HIGH状态3秒
        System.out.println();
        log.info("========> 脉冲输出状态至[高电平]持续 3 秒 <========");
        digitalOutput.pulse(2, TimeUnit.SECONDS, DigitalState.HIGH);
        log.info("脉冲输出状态完成");
        System.out.println();

        // 关闭 Pi4J
        pi4j.shutdown();
    }

}
