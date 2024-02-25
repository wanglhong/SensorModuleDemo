package cn.wlih.core.base.service.impl;

import cn.wlih.core.base.mapper.MyBaseMapper;
import cn.wlih.core.base.service.MyBaseService;
import cn.wlih.core.config.ApplicationContextHolder;
import cn.wlih.core.myAnnotate.DbBaseField;
import cn.wlih.core.myAnnotate.VariableComment;
import cn.wlih.core.myEnum.DbBaseFieldType;
import cn.wlih.core.myEnum.dbEnum.IsDeleteEnum;
import cn.wlih.core.util.dbUtil.DbTableUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.extern.slf4j.Slf4j;
import supie.common.sequence.wrapper.IdGeneratorWrapper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;

@Slf4j
public abstract class MyBaseServiceImpl<M> extends ServiceImpl<MyBaseMapper<M>, M> implements MyBaseService<M> {

    @VariableComment("当前Service关联的主Model实体对象的Class")
    private final Class<M> modelClass;

    public MyBaseServiceImpl() {
        modelClass = (Class<M>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @VariableComment("获取子类中注入的Mapper类")
    protected abstract MyBaseMapper<M> mapper();

    @Override
    public MyBaseMapper<M> getBaseMapper() {
        return mapper();
    }

    @Override
    public Map<String, String> getModelJson(Class<M> modelClass) {
        Map<String, Map<String, Object>> modelClassFieldsMap = DbTableUtil.collectJavaFields(modelClass);
        Set<String> modelClassFieldsMapKeySet = modelClassFieldsMap.keySet();
        Map<String, String> resultData = new LinkedHashMap<>();
        for (String modelClassFieldsMapKey : modelClassFieldsMapKeySet) {
            Map<String, Object> modelClassField = modelClassFieldsMap.get(modelClassFieldsMapKey);
            StringBuilder fieldComment = new StringBuilder((String) modelClassField.get("fieldComment"));
            Class<?> fieldTypeClass = (Class<?>) modelClassField.get("fieldTypeClass");
            StringBuilder fieldTypeStr = new StringBuilder();
            // 判断 fieldTypeClass 是否属于枚举
            if (fieldTypeClass.isEnum()) {
                List<Object> enumFieldValueByFieldAnnotationList = getEnumFieldValueByFieldAnnotation(fieldTypeClass, JsonValue.class);
                for (int i = 0, listSize = enumFieldValueByFieldAnnotationList.size(); i < listSize; i++) {
                    if (i == 0) {
                        fieldTypeStr.append("['");
                    } else {
                        fieldTypeStr.append(", '");
                    }
                    fieldTypeStr.append(String.valueOf(enumFieldValueByFieldAnnotationList.get(i))).append("'");
                    if (i == listSize - 1) {
                        fieldTypeStr.append("]");
                    }
                }
            } else {
                fieldTypeStr.append(fieldTypeClass.getSimpleName());
            }
            resultData.put(modelClassFieldsMapKey, fieldComment.append(" —— ").append(fieldTypeStr).toString());
        }
        return resultData;
    }

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
     * 新增数据
     *
     * @param m 需要新增的数据
     * @return 成功返回true，失败返回false
     */
    @Override
    public M add(M m) {
        this.buildBaseFieldsValue(m, true);
        if (mapper().insert(m) == 0) {
            setDbBaseFieldValue(m, DbBaseFieldType.ID, null);
        }
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
