package cn.wlih.demo.Pi4j;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.*;

import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 *
 * @author 王立宏
 * 描述: 数字输出输入调用
 * path: SensorModuleDemo-cn.wlih.demo.Pi4j-DigitalOutputAndInput
 * date: 2023/9/23 1:13
 */
public class DigitalOutputAndInput {

    private static final int PIN_OUTPUT = 4; //GPIIO4--->BCM4
    private static final int PIN_INPUT = 22; //GPIIO22--->BCM22

    public void demo01() {
        //初始化Pi4j
        Context pi4j = Pi4J.newAutoContext();
        DigitalOutputConfigBuilder outputConfig = DigitalOutput.newConfigBuilder(pi4j)
                .id("outputId-HA")
                .name("outputName-HA")
                .address(PIN_OUTPUT)
                .shutdown(DigitalState.LOW)
                .initial(DigitalState.LOW);
        DigitalOutput output = pi4j.dout().create(outputConfig);

        Properties properties = new Properties();
        properties.put("id", "my_digital_input");
        properties.put("name", "MY-DIGITAL-INPUT");
        properties.put("address", PIN_INPUT);
        properties.put("pull", "UP");
        DigitalInputConfig inputConfig = DigitalInput.newConfigBuilder(pi4j)
                .load(properties).build();
        DigitalInput input = pi4j.din().create(inputConfig);


    }

}
