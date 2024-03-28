package cn.wlih.upms.config;

import cn.wlih.core.sequence.wrapper.IdGeneratorWrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
