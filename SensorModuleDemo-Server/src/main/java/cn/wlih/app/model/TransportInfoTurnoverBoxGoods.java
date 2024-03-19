package cn.wlih.app.model;

import cn.wlih.core.base.model.BaseModel;
import cn.wlih.core.myAnnotate.ClassComment;
import cn.wlih.core.myAnnotate.VariableComment;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("sm_transport_info_turnover_box_goods")
@ClassComment("运输信息-周转箱-货物关联表（1*N*M）")
@EqualsAndHashCode(callSuper = true)
public class TransportInfoTurnoverBoxGoods extends BaseModel {

    @VariableComment("运输信息ID")
    private Long transportInfoId;

    @VariableComment("周转箱ID")
    private Long turnoverBoxId;

    @VariableComment("货物ID")
    private Long goodsId;

    @VariableComment("货物数量")
    private Integer goodsNum;

}
