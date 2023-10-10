package cn.wlih;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 描述: 程序启动类
 * @author 王立宏
 * @date 2023/8/25 9:37
 * @path SensorModuleDemo-cn.wlih-ServerApplication
 */
@Slf4j
@SpringBootApplication
public class ServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }
}
