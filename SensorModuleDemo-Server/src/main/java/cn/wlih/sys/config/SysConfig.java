package cn.wlih.sys.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import supie.common.sequence.wrapper.IdGeneratorWrapper;

/**
 * 手动注入配置
 */
@Configuration
public class SysConfig {

    @Bean
    public IdGeneratorWrapper idGeneratorWrapper() {
        // 初始化IdGeneratorWrapper的实例
        return new IdGeneratorWrapper();
    }

}
