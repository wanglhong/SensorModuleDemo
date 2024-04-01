package cn.wlih.app.model;

import cn.wlih.app.model.modelDbEnum.FileType;
import cn.wlih.core.base.model.BaseModel;
import cn.wlih.core.myAnnotate.ClassComment;
import cn.wlih.core.myAnnotate.UploadFlagColumn;
import cn.wlih.core.myAnnotate.VariableComment;
import cn.wlih.core.upload.UploadStoreTypeEnum;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("sm_business_file")
@ClassComment("基础附件表")
@EqualsAndHashCode(callSuper = true)
public class BusinessFile extends BaseModel {

    @VariableComment("文件名称")
    private String fileName;

    @VariableComment("文件类型")
    private FileType fileType;

    @VariableComment("文件大小")
    private Long fileSize;

    @VariableComment("文件Key")
    @UploadFlagColumn(bucketName = "SensorModuleDev", storeType = UploadStoreTypeEnum.LOCAL_SYSTEM)
    private String fileKey;

}
