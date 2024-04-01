package cn.wlih.core.util;

import cn.wlih.core.myAnnotate.DbBaseField;
import cn.wlih.core.myEnum.DbBaseFieldType;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Java Class相关操作类
 */
public class MyClazzUtil {

    /**
     * 获取枚举中具有指定注解的字段的值
     * @param enumClass 枚举类
     * @param annotationClass 注解类
     * @return
     * @param <A>
     */
    public static <A extends Annotation> List<Object> getEnumFieldValueByFieldAnnotation(Class<?> enumClass, Class<A> annotationClass) {
        List<Object> enumFieldValueList = new ArrayList<>();
        if (enumClass.isEnum()) {
            for (Object enumConstant : enumClass.getEnumConstants()) {
                Field[] fields = enumConstant.getClass().getDeclaredFields();
                for (Field field : fields) {
                    if (field.isAnnotationPresent(annotationClass)) {
                        // 枚举中获取该字段的值，并添加到 enumFieldValueList 中
                        field.setAccessible(true); // 确保私有字段也可以访问
                        try {
                            enumFieldValueList.add(field.get(enumConstant));
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    }
                }
            }
        }
        return enumFieldValueList;
    }

    /**
     * 设置数据库基础字段的值
     * @param m 需要处理的对象
     * @param dbBaseFieldType 数据库基础字段的类型
     * @return 是否设置成功
     * @param <M>
     */
    public static <M> Field getDbBaseField(M m, DbBaseFieldType dbBaseFieldType) {
        Class<?> cls = m.getClass();
        while (cls != null && cls != Object.class) {
            Field[] fields = cls.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(DbBaseField.class)) {
                    if (field.getAnnotation(DbBaseField.class).type().equals(dbBaseFieldType)) {
                        return field;
                    }
                }
            }
            cls = cls.getSuperclass(); // 继续查找父类
        }
        // 在所有类中都未找到符合条件的字段
        return null;
    }
    public static Field getDbBaseField(Class<?> clzz, DbBaseFieldType dbBaseFieldType) {
        while (clzz != null && clzz != Object.class) {
            Field[] fields = clzz.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(DbBaseField.class)) {
                    if (field.getAnnotation(DbBaseField.class).type().equals(dbBaseFieldType)) {
                        return field;
                    }
                }
            }
            clzz = clzz.getSuperclass(); // 继续查找父类
        }
        // 在所有类中都未找到符合条件的字段
        return null;
    }

    /**
     * 设置数据库基础字段的值
     * @param m 需要处理的对象
     * @param dbBaseFieldType 数据库基础字段的类型
     * @param fieldValue 字段的值
     * @return 是否设置成功
     * @param <M>
     */
    public static <M> boolean setDbBaseFieldValue(M m, DbBaseFieldType dbBaseFieldType, Object fieldValue) {
        Field dbBaseField = getDbBaseField(m, dbBaseFieldType);
        if (dbBaseField == null) {
            return false;
        }
        return setFieldValue(m, dbBaseField, fieldValue);
    }

    /**
     * 设置数据库基础字段的值
     * @param m 需要处理的对象
     * @param field 数据库基础字段的类型
     * @param fieldValue 字段的值
     * @return 是否设置成功
     * @param <M>
     */
    public static <M> boolean setFieldValue(M m, Field field, Object fieldValue) {
        try {
            field.setAccessible(true);
            field.set(m, fieldValue);
            return true; // 成功设置值
        } catch (IllegalAccessException e) {
            return false; // 设置失败
        }
    }

    public static <M> Object getFieldValue(M m, String fieldName) {
        try {
            Field field = m.getClass().getDeclaredField(fieldName);
            return getFieldValue(m, field);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public static <M> Object getFieldValue(M m, Field field) {
        try {
            field.setAccessible(true);
            return field.get(m);
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    /**
     * 获取包括父类在内的所有字段
     *
     * @param clazz 类的Class对象
     * @return 包含所有字段的列表
     */
    public static List<Field> getAllFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        while (clazz != null) {
            // 添加当前类声明的字段
            for (Field field : clazz.getDeclaredFields()) {
                fields.add(field);
            }
            // 获取父类的Class对象
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

}
