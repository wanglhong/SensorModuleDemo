package cn.wlih.sensormodule.vo;

import cn.wlih.core.base.vo.BaseModelVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Schema(title = "视频信息表")
@EqualsAndHashCode(callSuper = true)
public class VideoInfoVo extends BaseModelVo {

    @Schema(title = "运输信息ID")
    Long transportInfoId;

    @Schema(title = "基础附件ID")
    Long baseAttachmentId;

}
