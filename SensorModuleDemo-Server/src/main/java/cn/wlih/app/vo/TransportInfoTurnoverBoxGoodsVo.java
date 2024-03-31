package cn.wlih.app.vo;

import cn.wlih.core.base.vo.BaseModelVo;
import cn.wlih.upms.model.SysUser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Schema(defaultValue = "运输信息-周转箱-货物关联表（1*N*M）")
@EqualsAndHashCode(callSuper = true)
public class TransportInfoTurnoverBoxGoodsVo extends BaseModelVo {

    @Schema(defaultValue = "运输信息ID")
    private Long transportInfoId;

    @Schema(defaultValue = "周转箱ID")
    private Long turnoverBoxId;

    @Schema(defaultValue = "货物ID")
    private Long goodsId;

    @Schema(defaultValue = "货物数量")
    private Integer goodsNum;

    @Schema(defaultValue = "货物装箱操作员ID")
    private Long goodsToBoxUserId;

    @Schema(defaultValue = "周转箱装车操作员ID")
    private Long boxToTransportEquipmentUserId;

    @Schema(defaultValue = "备注")
    private String remark;

    @Schema(defaultValue = "货物装箱操作员信息")
    private SysUser goodsToBoxUser;

    @Schema(defaultValue = "周转箱装车操作员信息")
    private SysUser boxToTransportEquipmentUser;

}
