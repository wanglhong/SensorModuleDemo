package cn.wlih.app.vo;

import cn.wlih.core.base.vo.BaseModelVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Schema(defaultValue = "清关信息与附件多对多关联表")
@EqualsAndHashCode(callSuper = true)
public class ClearanceInfoBusinessFileVo extends BaseModelVo {

    @Schema(defaultValue = "清关信息ID")
    private Long clearanceInfoId;

    @Schema(defaultValue = "附件ID")
    private Long businessFileId;

}
