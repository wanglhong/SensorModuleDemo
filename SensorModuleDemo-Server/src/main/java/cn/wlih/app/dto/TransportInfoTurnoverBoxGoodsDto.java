package cn.wlih.app.dto;

import cn.wlih.core.base.dto.BaseModelDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Schema(defaultValue = "运输信息-周转箱-货物关联表（1*N*M）")
@EqualsAndHashCode(callSuper = true)
public class TransportInfoTurnoverBoxGoodsDto extends BaseModelDto {

    @Schema(defaultValue = "运输信息ID")
    private Long transportInfoId;

    @Schema(defaultValue = "周转箱ID")
    private Long turnoverBoxId;

    @Schema(defaultValue = "货物ID")
    private Long goodsId;

    @Schema(defaultValue = "货物数量")
    private Integer goodsNum;

}
