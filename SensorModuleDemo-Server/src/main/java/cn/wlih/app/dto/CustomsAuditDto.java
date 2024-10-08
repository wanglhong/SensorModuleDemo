package cn.wlih.app.dto;

import cn.wlih.app.model.modelDbEnum.AuditState;
import cn.wlih.app.model.modelDbEnum.AuditType;
import cn.wlih.core.base.dto.BaseModelDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Schema(title = "海关审核表")
@EqualsAndHashCode(callSuper = true)
public class CustomsAuditDto extends BaseModelDto {

    @Schema(title = "审批名称")
    private String auditName;

    @Schema(title = "审批提交组织ID")
    private Long organizationIdOfSubmit;

    @Schema(title = "审批提交部门ID")
    private Long deptIdOfSubmit;

    @Schema(title = "审批提交人ID")
    private Long userIdOfSubmit;

    @Schema(title = "审批类型")
    private AuditType auditType;

    @Schema(title = "报关信息ID")
    private Long customsDeclarationInfoId;

    @Schema(title = "清关信息ID")
    private Long customsClearanceInfoId;

    @Schema(title = "审批状态")
    private AuditState auditState;

    @Schema(title = "备注")
    private String remark;

    @Schema(title = "审批人ID")
    private Long userIdOfAudit;

}
