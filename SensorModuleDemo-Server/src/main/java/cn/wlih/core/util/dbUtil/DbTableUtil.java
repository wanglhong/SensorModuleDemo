package cn.wlih.core.util.dbUtil;

import cn.hutool.core.util.StrUtil;
import cn.wlih.core.myAnnotate.ClassComment;
import cn.wlih.core.myAnnotate.VariableComment;
import cn.wlih.core.util.NameFormatConversionUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
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
                tableName = NameFormatConversionUtil.convertCamelToSnake(clazz.getSimpleName());
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
    public static String collectDbFields(Class<?> clazz, String primaryKeyID, List<Map<String, String>> dbFieldList) {
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
            String fieldName = NameFormatConversionUtil.convertCamelToSnake(field.getName());
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
     * 递归收集Java对象的数据库映射字段
     * @param clazz Java对象
     * @return {"${字段变量名}": {"fieldTypeClass": "Class<?> ${字段类型}", "fieldComment": "${字段注释}"}}
     */
    public static Map<String, Map<String, Object>> collectJavaFields(Class<?> clazz) {
        // 递归终止条件：如果已经到达了Object类
        if (clazz == null || clazz == Object.class) {
            return new LinkedHashMap<>();
        }
        // 先递归调用，确保父类的字段先被添加
        Map<String, Map<String, Object>> resultDataMap = collectJavaFields(clazz.getSuperclass());
        // 添加当前类声明的字段
        for (Field field : clazz.getDeclaredFields()) {
            TableField tableField = field.getAnnotation(TableField.class);
            if (null != tableField && !tableField.exist()) {
                continue;
            }
            Map<String, Object> dbFieldMap = new HashMap<>(2);
            // 字段类型
            dbFieldMap.put("fieldTypeClass", field.getType());
            // 字段注释
            dbFieldMap.put("fieldComment", field.getAnnotation(VariableComment.class).value());
            // key 为字段名称
            resultDataMap.put(field.getName(), dbFieldMap);
        }
        return resultDataMap;
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
