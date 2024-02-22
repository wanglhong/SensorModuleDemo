package cn.wlih.core.myAnnotate;

import cn.wlih.core.myEnum.DbBaseFieldType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 用于标注数据库中的基础字段
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface DbBaseField {
    DbBaseFieldType type();
}
