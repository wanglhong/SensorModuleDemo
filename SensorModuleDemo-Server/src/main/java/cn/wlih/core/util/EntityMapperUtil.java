package cn.wlih.core.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class EntityMapperUtil {

    public static <T, U> U convert(T source, Class<U> targetClass) throws InstantiationException, IllegalAccessException {
        U target = targetClass.newInstance();
        List<Field> sourceFields = MyClazzUtil.getAllFields(source.getClass());
        List<Field> targetFields = MyClazzUtil.getAllFields(targetClass);
        for (Field sourceField : sourceFields) {
            sourceField.setAccessible(true);
            String sourceFieldName = sourceField.getName();
            for (Field targetField : targetFields) {
                targetField.setAccessible(true);
                String targetFieldName = targetField.getName();
                if (sourceFieldName.equals(targetFieldName)) {
                    Object value;
                    if (sourceField.getType().equals(targetField.getType())) {
                        value = sourceField.get(source);
                    } else {
                        value = EntityMapperUtil.convert(sourceField.get(source), targetField.getType());
                    }
                    targetField.set(target, value);
                    break;
                }
            }
        }
        return target;
    }

}
