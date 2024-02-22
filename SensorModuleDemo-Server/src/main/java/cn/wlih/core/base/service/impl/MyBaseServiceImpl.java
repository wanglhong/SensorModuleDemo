package cn.wlih.core.base.service.impl;

import cn.wlih.core.base.service.MyBaseService;
import cn.wlih.core.config.ApplicationContextHolder;
import cn.wlih.core.myAnnotate.DbBaseField;
import cn.wlih.core.myEnum.DbBaseFieldType;
import cn.wlih.core.myEnum.dbEnum.IsDeleteEnum;
import cn.wlih.core.util.NameFormatConversionUtil;
import cn.wlih.core.util.dbUtil.DbTableUtil;
import lombok.extern.slf4j.Slf4j;
import supie.common.sequence.wrapper.IdGeneratorWrapper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

@Slf4j
public abstract class MyBaseServiceImpl<M> implements MyBaseService<M> {

    @Override
    public Map<String, String> getModelJson(Class<M> modelClass) {
        List<Map<String, String>> classFieldMapList = new LinkedList<>();
        DbTableUtil.collectDbFields(modelClass, null, classFieldMapList);
        Map<String, String> resultData = new LinkedHashMap<>();
        for (Map<String, String> classFieldMap : classFieldMapList) {
            resultData.put(classFieldMap.get("fieldName"),
                    classFieldMap.get("fieldComment") + " —— " + classFieldMap.get("fieldType"));
        }
        return resultData;
    }

    /**
     * 新增数据
     *
     * @param m 需要新增的数据
     * @return 成功返回true，失败返回false
     */
    @Override
    public M add(M m) {
        this.buildBaseFieldsValue(m, true);
        return m;
    }

    /**
     * 获取新ID
     * @return 新ID
     */
    protected Long getNewId() {
        return ApplicationContextHolder.getBean(IdGeneratorWrapper.class).nextLongId();
    }

    /**
     * 构建基础字段的值
     * @param m 需要处理的对象
     */
    private void buildBaseFieldsValue(M m, Boolean isAdd) {
        Date nowDate = new Date();
        Long loginUserId = 1001L;
        if (isAdd) {
            // 新增，需要设置 ID、创建时间、创建人ID、逻辑删除
            setDbBaseFieldValue(m, DbBaseFieldType.ID, getNewId());
            setDbBaseFieldValue(m, DbBaseFieldType.CREATE_TIME, nowDate);
            setDbBaseFieldValue(m, DbBaseFieldType.CREATE_USER_ID, loginUserId);
            setDbBaseFieldValue(m, DbBaseFieldType.IS_DELETE, IsDeleteEnum.EXIST);
        }
        // 设置修改时间、修改人ID
        setDbBaseFieldValue(m, DbBaseFieldType.UPDATE_TIME, nowDate);
        setDbBaseFieldValue(m, DbBaseFieldType.UPDATE_USER_ID, loginUserId);
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
        Class<?> cls = m.getClass();
        while (cls != null && cls != Object.class) {
            Field[] fields = cls.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(DbBaseField.class)) {
                    if (field.getAnnotation(DbBaseField.class).type().equals(dbBaseFieldType)) {
                        try {
                            field.setAccessible(true);
                            field.set(m, fieldValue); // TODO 实验: field.set(m, fieldValue)中 field 与 m 是否有必然联系?
                            log.debug("{} set for field: {} in class {}.", fieldValue, field.getName(), cls.getSimpleName());
                            return true; // 成功设置值
                        } catch (IllegalAccessException e) {
                            log.warn("Failed to set [{}] for field: {} in class {}. ErrorMessage: {}",
                                    fieldValue, field.getName(), cls.getSimpleName(), e.getMessage());
                            return false; // 设置失败
                        }
                    }
                }
            }
            cls = cls.getSuperclass(); // 继续查找父类
        }
        return false; // 在所有类中都未找到符合条件的字段
    }

}
