package cn.wlih.core.fileupdownload;

import cn.wlih.core.myAnnotate.ClassComment;
import cn.wlih.core.myAnnotate.VariableComment;

@ClassComment("上传数据存储介质类型")
public enum UploadStoreTypeEnum {

    @VariableComment("本地存储")
    LOCAL_SYSTEM,

    @VariableComment("minio分布式存储")
    MINIO_SYSTEM,

    @VariableComment("阿里云OSS存储")
    ALIYUN_OSS_SYTEM,

    @VariableComment("腾讯云COS存储")
    QCLOUD_COS_SYTEM,

    @VariableComment("华为云OBS存储")
    HUAWEI_OBS_SYSTEM

}
