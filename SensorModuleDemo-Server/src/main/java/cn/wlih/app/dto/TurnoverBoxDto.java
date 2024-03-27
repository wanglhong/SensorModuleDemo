package cn.wlih.app.dto;

import cn.wlih.app.model.modelDbEnum.TurnoverBoxState;
import cn.wlih.core.base.dto.BaseModelDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@Schema(defaultValue = "周转箱信息表")
@EqualsAndHashCode(callSuper = true)
public class TurnoverBoxDto extends BaseModelDto {

    @Schema(defaultValue = "RFID编码")
    private String rfidCode;

    @Schema(defaultValue = "周转箱名称")
    private String turnoverBoxName;

    @Schema(defaultValue = "周转箱体积（单位：cm³）")
    private BigDecimal turnoverBoxVolume;

    @Schema(defaultValue = "周转箱容积（单位：cm³）")
    private BigDecimal turnoverBoxContainer;

    @Schema(defaultValue = "周转箱重量（单位：克）")
    private BigDecimal turnoverBoxWeight;

    @Schema(defaultValue = "周转箱状态（1：闲置中、2：使用中、3：废弃）")
    private TurnoverBoxState turnoverBoxState;

    @Schema(defaultValue = "周转箱描述")
    private String turnoverBoxRemark;

}
