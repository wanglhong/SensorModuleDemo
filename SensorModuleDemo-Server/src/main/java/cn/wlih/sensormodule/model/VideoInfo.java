package cn.wlih.sensormodule.model;

import cn.wlih.core.base.model.BaseModel;
import cn.wlih.core.myAnnotate.ClassComment;
import cn.wlih.core.myAnnotate.VariableComment;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("sm_video_info")
@ClassComment("视频信息表")
@EqualsAndHashCode(callSuper = true)
public class VideoInfo extends BaseModel {

    @VariableComment("运输信息ID")
    Long transportInfoId;

    @VariableComment("基础附件ID")
    Long baseAttachmentId;

}
