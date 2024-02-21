package cn.wlih.core.myAnnotate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 类注释
 */
@Target(value = {ElementType.TYPE}) // 注解的使用范围 -- 类
@Retention(RetentionPolicy.RUNTIME) // 注解的保留策略 -- 运行时
public @interface ClassComment {
    String value() default "";
}
