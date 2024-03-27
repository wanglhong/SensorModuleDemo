package cn.wlih.app.vo;

import cn.wlih.core.base.vo.BaseModelVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@Schema(defaultValue = "货物信息表")
@EqualsAndHashCode(callSuper = true)
public class GoodsVo extends BaseModelVo {

    @Schema(defaultValue = "货物名称")
    private String goodsName;

    @Schema(defaultValue = "货物编码")
    private String goodsCode;

    @Schema(defaultValue = "单位货物价值（单位：分）")
    private BigDecimal goodsUnitValue;

    @Schema(defaultValue = "货物体积（单位：cm³）")
    private BigDecimal goodsUnitVolume;

    @Schema(defaultValue = "货物重量（单位：克）")
    private BigDecimal goodsUnitWeight;

    @Schema(defaultValue = "货物备注")
    private String goodsRemark;

}
