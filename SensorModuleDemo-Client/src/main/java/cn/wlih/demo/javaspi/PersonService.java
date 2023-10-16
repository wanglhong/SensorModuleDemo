package cn.wlih.demo.javaspi;

/**
 * 描述：JavaSpiDemo
 * 使用 Java SPI 时，需要注意以下几点：
 *     接口必须是公共的，且只能包含抽象方法。
 *     实现类必须有一个无参构造函数。
 *     配置文件中指定的类必须是实现了相应接口的非抽象类。
 *     配置文件必须放在 META-INF/services 目录下。
 *     配置文件的文件名必须为接口的全限定名。
 *
 * @author 王立宏
 * @date 2023/10/16 10:19
 * @path SensorModuleDemo-cn.wlih.demo.javaspi-PersonService
 */
public interface PersonService {

    /**
     * 讲话
     *
     * @author 王立宏
     * @date 2023/10/16 10:22
     */
    void sayHello();

}
