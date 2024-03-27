package cn.wlih.core.util;

import cn.wlih.app.model.TurnoverBox;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.lang.reflect.Field;

public class JsonUtil {

    public static <T> T jsonToObject(String jsonStr, Class<T> parameterType) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 忽略未知属性
        T result = objectMapper.readValue(jsonStr, parameterType);
        handleEnumWithJsonValueAnnotation(result);
        return result;
    }

    private static void handleEnumWithJsonValueAnnotation(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getType().isEnum()) {
                try {
                    field.setAccessible(true);
                    Object enumValue = field.get(obj);
                    if (enumValue != null) {
                        // Check if the enum field has JsonValue annotation
                        JsonValue jsonValueAnnotation = field.getAnnotation(JsonValue.class);
                        if (jsonValueAnnotation != null) {
                            // If the enum field has JsonValue annotation, find the corresponding enum constant
                            Object enumConstant = findEnumConstant(obj.getClass(), enumValue);
                            if (enumConstant != null) {
                                field.set(obj, enumConstant);
                            }
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static Object findEnumConstant(Class<?> enumType, Object enumValue) {
        for (Object constant : enumType.getEnumConstants()) {
            try {
                Field field = constant.getClass().getDeclaredField("value");
                field.setAccessible(true);
                if (field.get(constant).equals(enumValue)) {
                    return constant;
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
