package cn.wlih.core.sequence.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * common-sequence模块的配置类。
 *
 * @author rm -rf .bug
 * @date 2024-01-02
 */
@Data
@ConfigurationProperties(prefix = "common-sequence")
public class IdGeneratorProperties {

    /**
     * 基础版生成器所需的WorkNode参数值。仅当advanceIdGenerator为false时生效。
     */
    private Integer snowflakeWorkNode = 1;

}
