package cn.wlih.app.dto;

import cn.wlih.core.base.dto.BaseModelDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@Schema(title = "货物信息表")
@EqualsAndHashCode(callSuper = true)
public class GoodsDto extends BaseModelDto {

    @Schema(title = "货物名称")
    private String goodsName;

    @Schema(title = "货物编码")
    private String goodsCode;

    @Schema(title = "单位货物价值（单位：分）")
    private BigDecimal goodsUnitValue;

    @Schema(title = "货物体积（单位：cm³）")
    private BigDecimal goodsUnitVolume;

    @Schema(title = "货物重量（单位：克）")
    private BigDecimal goodsUnitWeight;

    @Schema(title = "货物备注")
    private String goodsRemark;

}
