package cn.wlih.core.util.dbUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

class JavaDatabaseType {

    /**
     * 获取Logger对象
     */
    private static final Logger log = LoggerFactory.getLogger(JavaDatabaseType.class);
    private static final Map<Class<?>, String> typeMapping = new HashMap<>();

    static {
        // 初始化映射
        typeMapping.put(String.class, "VARCHAR(255)");
        typeMapping.put(int.class, "INT");
        typeMapping.put(Integer.class, "INT");
        typeMapping.put(long.class, "BIGINT");
        typeMapping.put(Long.class, "BIGINT");
        typeMapping.put(boolean.class, "BOOLEAN");
        typeMapping.put(Boolean.class, "BOOLEAN");
        typeMapping.put(java.util.Date.class, "DATETIME");
        typeMapping.put(java.sql.Date.class, "DATE");
        typeMapping.put(double.class, "DOUBLE");
        typeMapping.put(Double.class, "DOUBLE");
        typeMapping.put(float.class, "FLOAT");
        typeMapping.put(Float.class, "FLOAT");
        typeMapping.put(BigDecimal.class, "DECIMAL(19, 4)");
    }

    public static String getDatabaseType(Class<?> javaTypeClass) {
        if (javaTypeClass.isEnum()) {
            // 枚举类型
            return "INT";
        } else if (!typeMapping.containsKey(javaTypeClass)) {
            log.warn("未知的Java类型[{}]，将映射为数据库[{}]类型",
                    javaTypeClass.getName(), "VARCHAR(255)");
        }
        // 获取映射的数据库类型，如果不存在则返回默认值
        return typeMapping.getOrDefault(javaTypeClass, "VARCHAR(255)");
    }
}
