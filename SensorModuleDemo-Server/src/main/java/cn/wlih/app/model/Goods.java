package cn.wlih.app.model;

import cn.wlih.core.base.model.BaseModel;
import cn.wlih.core.myAnnotate.ClassComment;
import cn.wlih.core.myAnnotate.VariableComment;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@TableName("goods")
@ClassComment("货物信息表")
@EqualsAndHashCode(callSuper = true)
public class Goods extends BaseModel {

    @VariableComment("货物名称")
    private String goodsName;

    @VariableComment("货物编码")
    private String goodsCode;

    @VariableComment("单位货物价值（单位：分）")
    private BigDecimal goodsUnitValue;

    @VariableComment("货物体积（单位：cm³）")
    private BigDecimal goodsUnitVolume;

    @VariableComment("货物重量（单位：克）")
    private BigDecimal goodsUnitWeight;

    @VariableComment("货物描述")
    private String goodsDescription;

}
