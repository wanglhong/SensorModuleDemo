package cn.wlih.core.sequence.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * common-sequence模块的配置类。
 *
 * @author rm -rf .bug
 * @date 2024-01-02
 */
@Data
@Component
public class IdGeneratorProperties {

    /**
     * 基础版生成器所需的WorkNode参数值。仅当advanceIdGenerator为false时生效。
     */
    @Value("${common-sequence.snowflakeWorkNode:1}")
    private Integer snowflakeWorkNode;

}
