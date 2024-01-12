package cn.wlih.utils.factoryUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class CustomAnnotationProcessor {

    public static void process(Object instance) {
        Class<?> clazz = instance.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(MyPostConstruct.class)) {
                // 检查方法是否是public且无参数
                if (!Modifier.isPublic(method.getModifiers()) || method.getParameterCount() != 0) {
                    throw new IllegalStateException("带 @MyPostConstruct 注释的方法必须是公共的，并且没有参数：" + method);
                }
                method.setAccessible(true); // 允许访问private或protected方法
                try {
                    method.invoke(instance);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException("无法调用@MyPostConstruct方法：" + method, e);
                }
            }
        }
    }

}
