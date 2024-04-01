package cn.wlih.app.dto;

import cn.wlih.core.base.dto.BaseModelDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Schema(title = "海关报关信息表")
@EqualsAndHashCode(callSuper = true)
public class CustomsDeclarationInfoDto extends BaseModelDto {

    @Schema(title = "运输信息ID")
    private Long transportInfoId;

    @Schema(title = "报关单号")
    private String customsDeclarationNumber;

    @Schema(title = "HS编码")
    private String hsCode;

    @Schema(title = "货物价值（用于计算关税和税费）")
    private BigDecimal goodsValue;

    @Schema(title = "预计过境日期")
    private Date estimatedTransitDate;

    @Schema(title = "实际过境日期")
    private Date actualTransitDate;

}
