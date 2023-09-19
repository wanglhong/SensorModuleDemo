package cn.wlih.demo.Pi4j;

import com.pi4j.context.Context;
import com.pi4j.platform.Platform;
import com.pi4j.platform.Platforms;
import com.pi4j.provider.Providers;
import com.pi4j.registry.Registry;
import com.pi4j.util.Console;

/**
 * Created by IntelliJ IDEA.
 *
 * @author 王立宏
 * 描述: 用于输出有关 Pi4J 上下文的信息的帮助程序类。
 * 初始化Pi4J后，我们可以访问系统的以下核心部分
 * path: SensorModuleDemo-cn.wlih.demo.Pi4j-PrintInfo
 * date: 2023/9/1 1:23
 */
public class PrintInfo {


    /**
     * Pi4J Platforms.
     * <p>平台旨在表示运行 Pi4J 的硬件平台。在大多数情况下，这将是“RaspberryPi”平台，但Pi4J支持和可扩展的平台集，因此可以添加其他平台，如“BananaPi”，“Odroid”等。</p>
     * <p>平台表示系统硬件 IO 功能的物理布局以及目标平台支持的 IO 提供程序。例如，“RaspberryPi”平台支持“数字”输入和输出、PWM、I2C、SPI 和串行，但不支持“模拟”输入和输出的默认提供程序。</p>
     * <p>平台还提供 IO 引脚及其对目标硬件的功能的验证。</p>
     *
     * @param console {@link Console}
     * @param pi4j    {@link Context}
     */
    public static void printLoadedPlatforms(Console console, Context pi4j) {
        Platforms platforms = pi4j.platforms();

        // Let's print out to the console the detected and loaded
        // platforms that Pi4J detected when it was initialized.
        console.box("Pi4J 平台");
        console.println();
        platforms.describe().print(System.out);
        console.println();
    }

    /**
     * Pi4J 平台（默认平台）
     * <p>在Pi4J初始化期间，根据每个平台实现在运行时提供的加权值自动分配单个“默认”平台。此外，您可以在初始化后随时覆盖此行为并分配自己的“默认”平台。</p>
     * <p>默认平台是托管平台集合中的单个平台实例，用于定义 Pi4J 在创建和注册 IO 实例时将用于每个给定 IO 接口的默认 IO 提供程序。</p>
     *
     * @param console {@link Console}
     * @param pi4j    {@link Context}
     */
    public static void printDefaultPlatform(Console console, Context pi4j) {
        Platform platform = pi4j.platform();

        // Let's print out to the console the detected and loaded
        // platforms that Pi4J detected when it was initialized.
        console.box("Pi4J 默认平台");
        console.println();
        platform.describe().print(System.out);
        console.println();
    }

    /**
     * Pi4J 供应商
     * <p>
     * 提供程序旨在表示 IO 实现，并提供对系统上可用的 IO 接口的访问。提供程序“提供”IO 接口的具体运行时实现，例如：
     * <ul>
     *  <li>DigitalInput（数字输入）</li>
     *  <li>DigitalOutput（数字输出）</li>
     *  <li>AnalogInput（模拟输入）</li>
     *  <li>AnalogOutput（模拟输出）</li>
     *  <li>PWM</li>
     *  <li>I2C</li>
     *  <li>SPI</li>
     *  <li>SERIAL（串行）</li>
     * </ul>
     * </p>
     * <p>
     * 每个平台都将分配有一组默认提供程序，以用作将在给定平台的硬件 IO 上使用的默认提供程序。
     * 但是，您不限于平台提供的提供程序，您可以使用已在 Pi4J 系统上注册的任何提供程序实例化 IO 接口。
     * 一个很好的例子是“模拟输入”和“模拟输出”IO接口。“RaspberryPi”本身不支持模拟IO硬件，
     * 但是将附加的ADC（模数转换器）或DAC（数模转换器）芯片连接到数据总线（I2CSPI），您可能希望使用Pi4J读取这些模拟硬件接口。
     * </p>
     * <p>
     * 提供程序允许完全灵活且可扩展的基础架构，使第三方能够通过编写自己的提供程序实现库来构建和扩展Pi4J的功能。
     * </p>
     *
     * @param console {@link Console}
     * @param pi4j    {@link Context}
     */
    public static void printProviders(Console console, Context pi4j) {
        Providers providers = pi4j.providers();

        // Let's print out to the console the detected and loaded
        // providers that Pi4J detected when it was initialized.
        console.box("Pi4J 供应商");
        console.println();
        providers.describe().print(System.out);
        console.println();
    }

    /**
     * Pi4J 注册表
     * <p>注册表存储Pi4J管理的所有I/O的状态。</p>
     *
     * @param console {@link Console}
     * @param pi4j    {@link Context}
     */
    public static void printRegistry(Console console, Context pi4j) {
        Registry registry = pi4j.registry();

        // Let's print out to the console the detected and loaded
        // I/O interfaces registered with Pi4J and included in the 'Registry'.
        console.box("Pi4J 注册表");
        console.println();
        registry.describe().print(System.out);
        console.println();
    }

}
