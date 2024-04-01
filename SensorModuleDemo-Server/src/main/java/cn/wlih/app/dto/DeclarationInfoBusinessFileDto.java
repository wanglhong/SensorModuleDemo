package cn.wlih.app.dto;

import cn.wlih.core.base.dto.BaseModelDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Schema(title = "报关信息与附件多对多关联表")
@EqualsAndHashCode(callSuper = true)
public class DeclarationInfoBusinessFileDto extends BaseModelDto {

    @Schema(title = "清关信息ID")
    private Long declarationInfoId;

    @Schema(title = "附件ID")
    private Long businessFileId;

}
