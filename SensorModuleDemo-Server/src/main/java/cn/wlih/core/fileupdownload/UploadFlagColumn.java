package cn.wlih.core.fileupdownload;

import cn.wlih.core.fileupdownload.UploadStoreTypeEnum;

import java.lang.annotation.*;

/**
 * 用于标记支持数据上传和下载的字段。
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UploadFlagColumn {

    /**
     * 存储桶名称
     */
    String bucketName() default "";

    /**
     * 上传数据存储类型。
     *
     * @return 上传数据存储类型。
     */
    UploadStoreTypeEnum storeType();

}
