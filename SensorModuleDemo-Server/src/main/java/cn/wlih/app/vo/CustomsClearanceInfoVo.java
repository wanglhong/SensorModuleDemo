package cn.wlih.app.vo;

import cn.wlih.core.base.vo.BaseModelVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Schema(title = "海关清关信息表")
@EqualsAndHashCode(callSuper = true)
public class CustomsClearanceInfoVo extends BaseModelVo {

    @Schema(title = "清关信息名称")
    private String customsClearanceName;

    @Schema(title = "运输信息ID")
    private Long transportInfoId;

    @Schema(title = "报关信息ID")
    private Long customsDeclarationInfoId;

    @Schema(title = "清关单号")
    private String customsClearanceNumber;

    @Schema(title = "审批机构ID")
    private Long organizationId;

    @Schema(title = "检验检疫结果")
    private String inspectionResult;

    @Schema(title = "关税和税费")
    private BigDecimal taxAndFees;

    @Schema(title = "放行日期")
    private Date clearanceDate;

    @Schema(title = "备注")
    private String remark;

}
