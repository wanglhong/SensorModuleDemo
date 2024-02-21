package cn.wlih.core.myAnnotate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 变量注释
 */
@Target({ElementType.FIELD, ElementType.METHOD}) // 注解在字段和方法上
@Retention(RetentionPolicy.RUNTIME) // 注解在运行时保留
public @interface VariableComment {
    String value() default "";
}
