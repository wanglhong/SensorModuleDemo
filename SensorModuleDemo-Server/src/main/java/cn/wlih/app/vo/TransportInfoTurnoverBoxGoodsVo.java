package cn.wlih.app.vo;

import cn.wlih.core.base.vo.BaseModelVo;
import cn.wlih.core.myAnnotate.VariableComment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Schema(defaultValue = "运输信息-周转箱-货物关联表（1*N*M）")
@EqualsAndHashCode(callSuper = true)
public class TransportInfoTurnoverBoxGoodsVo extends BaseModelVo {

    @VariableComment("运输信息ID")
    private Long transportInfoId;

    @VariableComment("周转箱ID")
    private Long turnoverBoxId;

    @VariableComment("货物ID")
    private Long goodsId;

    @VariableComment("货物数量")
    private Integer goodsNum;

}
