package cn.wlih;

import cn.wlih.core.util.LoggerUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.StopWatch;
import cn.hutool.core.util.StrUtil;

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
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        ConfigurableApplicationContext context = SpringApplication.run(ServerApplication.class, args);
        ServerProperties serverProperties = context.getBean(ServerProperties.class);
        Integer port = serverProperties.getPort();
        ServerProperties.Servlet servlet = serverProperties.getServlet();
        String contextPath = servlet.getContextPath();
        String urlSuffix = StrUtil.isBlank(contextPath) ? String.valueOf(port) : port+contextPath;

        LoggerUtil.logTitle(log, null, "SensorModuleDemo-Server",
                "Service startup complete.",
                "Startup time: " + stopWatch.getTotalTimeSeconds() + "s.",
                "Program Access Address: http://127.0.0.1:" + urlSuffix + ".");
//        LoggerUtil.logBox(log, null,
//                "Startup time: " + stopWatch.getTotalTimeSeconds() + "s",
//                "Program Access Address: http://127.0.0.1:" + urlSuffix);
    }

}
