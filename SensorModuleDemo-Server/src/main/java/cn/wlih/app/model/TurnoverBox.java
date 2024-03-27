package cn.wlih.app.model;

import cn.wlih.app.model.modelDbEnum.TurnoverBoxState;
import cn.wlih.core.base.model.BaseModel;
import cn.wlih.core.myAnnotate.ClassComment;
import cn.wlih.core.myAnnotate.VariableComment;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@TableName("sm_turnover_box")
@ClassComment("周转箱信息表")
@EqualsAndHashCode(callSuper = true)
public class TurnoverBox extends BaseModel {

    @VariableComment("RFID编码")
    private String rfidCode;

    @VariableComment("周转箱名称")
    private String turnoverBoxName;

    @VariableComment("周转箱体积（单位：cm³）")
    private BigDecimal turnoverBoxVolume;

    @VariableComment("周转箱容积（单位：cm³）")
    private BigDecimal turnoverBoxContainer;

    @VariableComment("周转箱重量（单位：克）")
    private BigDecimal turnoverBoxWeight;

    @VariableComment("周转箱状态（1：闲置中、2：使用中、3：废弃）")
    private TurnoverBoxState turnoverBoxState;

    @VariableComment("周转箱描述")
    private String turnoverBoxRemark;

}
