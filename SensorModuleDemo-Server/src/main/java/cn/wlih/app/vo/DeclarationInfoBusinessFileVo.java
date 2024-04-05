package cn.wlih.app.vo;

import cn.wlih.core.base.vo.BaseModelVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Schema(title = "报关信息与附件多对多关联表")
@EqualsAndHashCode(callSuper = true)
public class DeclarationInfoBusinessFileVo extends BaseModelVo {

    @Schema(title = "报关信息ID")
    private Long declarationInfoId;

    @Schema(title = "附件ID")
    private Long businessFileId;

    @Schema(title = "备注")
    private String remark;

}
