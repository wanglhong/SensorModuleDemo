package cn.wlih.core.util;

import cn.hutool.core.date.DateUtil;
import cn.wlih.app.model.TurnoverBox;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class JsonUtil {

    public static <T> T jsonToObject(String jsonStr, Class<T> parameterType) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Date.class, new CustomDateDeserializer());
        objectMapper.registerModule(module);
        // 设置日期解析的“宽松模式”
        objectMapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);
        // 忽略未知属性
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
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

/**
 * 自定义 ObjectMapper 日期解析器
 */
class CustomDateDeserializer extends JsonDeserializer<Date> {

    private static final DateFormat[] DATE_FORMATS = {
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"),
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    };

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String dateString = jsonParser.getText();
        for (DateFormat dateFormat : DATE_FORMATS) {
            try {
                return dateFormat.parse(dateString);
            } catch (ParseException e) {
                // Try the next date format
            }
        }
        throw new IOException("无法解析日期：" + dateString);
    }

}
