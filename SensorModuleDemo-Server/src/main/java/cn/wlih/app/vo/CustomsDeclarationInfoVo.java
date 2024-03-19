package cn.wlih.app.vo;

import cn.wlih.app.model.modelDbEnum.CustomsDeclarationState;
import cn.wlih.core.base.vo.BaseModelVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Schema(defaultValue = "海关报关信息表")
@EqualsAndHashCode(callSuper = true)
public class CustomsDeclarationInfoVo extends BaseModelVo {

    @Schema(defaultValue = "运输信息ID")
    private Long transportInfoId;

    @Schema(defaultValue = "报关单号")
    private String customsDeclarationNumber;

    @Schema(defaultValue = "HS编码")
    private String hsCode;

    @Schema(defaultValue = "货物价值（用于计算关税和税费）")
    private BigDecimal goodsValue;

    @Schema(defaultValue = "报关状态")
    private CustomsDeclarationState customsDeclarationState;

    @Schema(defaultValue = "预计过境日期")
    private Date estimatedTransitDate;

    @Schema(defaultValue = "实际过境日期")
    private Date actualTransitDate;

}
