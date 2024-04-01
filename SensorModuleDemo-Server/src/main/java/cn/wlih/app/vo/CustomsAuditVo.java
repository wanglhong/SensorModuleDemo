package cn.wlih.app.vo;

import cn.wlih.app.model.modelDbEnum.AuditState;
import cn.wlih.app.model.modelDbEnum.AuditType;
import cn.wlih.core.base.vo.BaseModelVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Schema(title = "海关审核表")
@EqualsAndHashCode(callSuper = true)
public class CustomsAuditVo extends BaseModelVo {

    @Schema(title = "审批提交组织ID")
    private Long organizationIdOfSubmit;

    @Schema(title = "审批提交部门ID")
    private Long deptIdOfSubmit;

    @Schema(title = "审批提交人ID")
    private Long userIdOfSubmit;

    @Schema(title = "审批类型")
    private AuditType auditType;

    @Schema(title = "审批信息ID")
    private Long auditInfoId;

    @Schema(title = "审批状态")
    private AuditState auditState;

    @Schema(title = "审批组织ID")
    private Long organizationIdOfAudit;

    @Schema(title = "审批部门ID")
    private Long deptIdOfAudit;

    @Schema(title = "审批人ID")
    private Long userIdOfAudit;

}
