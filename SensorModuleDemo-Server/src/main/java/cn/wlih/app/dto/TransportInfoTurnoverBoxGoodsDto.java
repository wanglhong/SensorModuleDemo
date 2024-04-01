package cn.wlih.app.dto;

import cn.wlih.core.base.dto.BaseModelDto;
import cn.wlih.upms.model.SysUser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Schema(title = "运输信息-周转箱-货物关联表（1*N*M）")
@EqualsAndHashCode(callSuper = true)
public class TransportInfoTurnoverBoxGoodsDto extends BaseModelDto {

    @Schema(title = "运输信息ID")
    private Long transportInfoId;

    @Schema(title = "周转箱ID")
    private Long turnoverBoxId;

    @Schema(title = "货物ID")
    private Long goodsId;

    @Schema(title = "货物数量")
    private Integer goodsNum;

    @Schema(title = "货物装箱操作员ID")
    private Long goodsToBoxUserId;

    @Schema(title = "周转箱装车操作员ID")
    private Long boxToTransportEquipmentUserId;

    @Schema(title = "备注")
    private String remark;

}
