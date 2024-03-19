package cn.wlih.app.dto;

import cn.wlih.app.model.modelDbEnum.CustomsClearanceState;
import cn.wlih.core.base.dto.BaseModelDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Schema(defaultValue = "海关清关信息表")
@EqualsAndHashCode(callSuper = true)
public class CustomsClearanceInfoDto extends BaseModelDto {

    @Schema(defaultValue = "运输信息ID")
    private Long transportInfoId;

    @Schema(defaultValue = "报关信息ID")
    private Long customsDeclarationInfoId;

    @Schema(defaultValue = "清关单号")
    private String customsClearanceNumber;

    @Schema(defaultValue = "清关状态（1：待审核、2：审核中、3：已放行）")
    private CustomsClearanceState customsClearanceState;

    @Schema(defaultValue = "审批机构ID")
    private Long organizationId;

    @Schema(defaultValue = "检验检疫结果")
    private String inspectionResult;

    @Schema(defaultValue = "关税和税费")
    private BigDecimal taxAndFees;

    @Schema(defaultValue = "放行日期")
    private Date clearanceDate;

    @Schema(defaultValue = "备注")
    private String remark;

}
