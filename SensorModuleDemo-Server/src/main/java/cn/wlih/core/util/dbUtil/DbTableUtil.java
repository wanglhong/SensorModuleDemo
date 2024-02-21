package cn.wlih.core.util.dbUtil;

import cn.hutool.core.util.StrUtil;
import cn.wlih.core.myAnnotate.ClassComment;
import cn.wlih.core.myAnnotate.VariableComment;
import cn.wlih.sys.model.SysUser;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.reflections.Reflections;
import org.reflections.scanners.Scanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

/**
 * 数据库表工具类
 */
public class DbTableUtil {

    /**
     * 生成建表语句
     * @param packageName 需要扫描的包名
     * @return 建表语句
     */
    public static String constructCreateTableSql(String packageName) {
        StringBuilder createTableSql = new StringBuilder();
        Set<Class<?>> annotatedClasses = findAnnotatedClasses(packageName, TableName.class);
        for (Class<?> clazz : annotatedClasses) {
            String tableName = clazz.getAnnotation(TableName.class).value();
            String tableDescription = clazz.getAnnotation(ClassComment.class).value();
            String primaryKeyID = "";
            if (!StrUtil.isBlank(tableName)) {
                tableName = convertCamelToSnake(clazz.getSimpleName());
            }
            createTableSql.append("-- ------------------------------------------------------------").append("\n");
            createTableSql.append("-- ").append(tableName).append(" | ").append(tableDescription).append("\n");
            createTableSql.append("-- ------------------------------------------------------------").append("\n");
            createTableSql.append("CREATE TABLE IF NOT EXISTS ").append(tableName).append(" (").append("\n");
            // 设置表字段
            List<Map<String, String>> dbFieldList = new LinkedList<>();
            primaryKeyID = collectDbFields(clazz, primaryKeyID, dbFieldList);
            for (int i = 0; i < dbFieldList.size(); i++) {
                Map<String, String> dbFieldMap = dbFieldList.get(i);
                String fieldName = dbFieldMap.get("fieldName");
                String fieldType = dbFieldMap.get("fieldType");
                String fieldComment = dbFieldMap.get("fieldComment");
                createTableSql.append("\t").append(fieldName).append(" ").append(fieldType);
                if (fieldName.equals(primaryKeyID)) {
                    createTableSql.append(" NOT");
                }
                createTableSql.append(" NULL COMMENT '").append(fieldComment);
                if (i == dbFieldList.size() - 1) {
                    createTableSql.append("'");
                } else {
                    createTableSql.append("',\n");
                }
            }
            // 设置主键ID
            if (StrUtil.isNotBlank(primaryKeyID)) {
                // 只在最后的PRIMARY KEY语句设置主键
                createTableSql.append(",\n").append("\t").append("PRIMARY KEY (").append(primaryKeyID).append(")\n");
            }
            createTableSql.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '").append(tableDescription).append("';\n");
        }
        return createTableSql.toString();
    }

    /**
     * 递归收集数据库字段
     *
     * @param clazz 类对象
     * @param dbFieldList 存储数据库字段的List。
     *  <p> 必须得先声明再传入，该方法只进行数值存入操作，不负责返回。 </p>
     *  <p>
     *      Map<String, String>：{"fieldName": "字段名称", "fieldType": "字段类型", "fieldComment": "字段注释"}
     *  </p>
     * @return 返回主键ID的名称
     */
    private static String collectDbFields(Class<?> clazz, String primaryKeyID, List<Map<String, String>> dbFieldList) {
        // 递归终止条件：如果已经到达了Object类
        if (clazz == null || clazz == Object.class) {
            return primaryKeyID;
        }
        // 先递归调用，确保父类的字段先被添加
        primaryKeyID = collectDbFields(clazz.getSuperclass(), primaryKeyID, dbFieldList);
        // 添加当前类声明的字段
        for (Field field : clazz.getDeclaredFields()) {
            TableField tableField = field.getAnnotation(TableField.class);
            if (null != tableField && !tableField.exist()) {
                continue;
            }
            Map<String, String> dbFieldMap = new HashMap<>(3);
            // 字段名称
            String fieldName = convertCamelToSnake(field.getName());
            // 字段注释
            String fieldComment = field.getAnnotation(VariableComment.class).value();
            // 判断是否属于主键ID
            TableId tableId = field.getAnnotation(TableId.class);
            if (null != tableId) {
                primaryKeyID = fieldName;
            }
            Class<?> fieldTypeClass = field.getType();
            String fieldType = JavaDatabaseType.getDatabaseType(fieldTypeClass);
            dbFieldMap.put("fieldName", fieldName);
            dbFieldMap.put("fieldType", fieldType);
            dbFieldMap.put("fieldComment", fieldComment);
            dbFieldList.add(dbFieldMap);
        }
        return primaryKeyID;
    }

    /**
     * 将驼峰命名转换为下划线命名
     * @param camelStr 驼峰命名
     * @return 下划线命名
     */
    public static String convertCamelToSnake(String camelStr) {
        // 如果输入为空或只有一个字符，直接返回输入
        if (camelStr == null || camelStr.isEmpty()) {
            return camelStr;
        }
        StringBuilder builder = new StringBuilder();
        // 将首字符处理为小写，确保结果符合规范
        builder.append(Character.toLowerCase(camelStr.charAt(0)));
        // 从第二个字符开始遍历
        for (int i = 1; i < camelStr.length(); i++) {
            char c = camelStr.charAt(i);
            // 如果是大写字符，则在前面添加下划线，并转换为小写
            if (Character.isUpperCase(c)) {
                builder.append('_').append(Character.toLowerCase(c));
            } else {
                // 如果不是大写字符，直接添加
                builder.append(c);
            }
        }
        return builder.toString();
    }

    /**
     * 查找带指定注解的类
     * @param packageName 包名
     * @param annotationClass 指定的类
     * @return
     * @param <T>
     */
    public static <T extends Annotation> Set<Class<?>> findAnnotatedClasses(String packageName, Class<T> annotationClass) {
        Reflections reflections = new Reflections(
                new ConfigurationBuilder().setUrls(ClasspathHelper.forPackage(packageName))
                        .setScanners(new TypeAnnotationsScanner(), new SubTypesScanner())
        );
        return reflections.getTypesAnnotatedWith(annotationClass);
    }

}

class JavaDatabaseType {

    /**
     * 获取Logger对象
     */
    private static final Logger log = LoggerFactory.getLogger(DbTableUtil.class);
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