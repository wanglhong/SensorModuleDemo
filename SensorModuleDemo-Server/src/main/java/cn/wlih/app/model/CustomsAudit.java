package cn.wlih.app.model;

import cn.wlih.app.model.modelDbEnum.AuditState;
import cn.wlih.app.model.modelDbEnum.AuditType;
import cn.wlih.core.base.model.BaseModel;
import cn.wlih.core.myAnnotate.ClassComment;
import cn.wlih.core.myAnnotate.VariableComment;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("sm_customs_audit")
@ClassComment("海关审核表")
@EqualsAndHashCode(callSuper = true)
public class CustomsAudit extends BaseModel {

    @VariableComment("审批提交组织ID")
    private Long organizationIdOfSubmit;

    @VariableComment("审批提交部门ID")
    private Long deptIdOfSubmit;

    @VariableComment("审批提交人ID")
    private Long userIdOfSubmit;

    @VariableComment("审批类型")
    private AuditType auditType;

    @VariableComment("审批信息ID")
    private Long auditInfoId;

    @VariableComment("审批状态")
    private AuditState auditState;

    @VariableComment("审批组织ID")
    private Long organizationIdOfAudit;

    @VariableComment("审批部门ID")
    private Long deptIdOfAudit;

    @VariableComment("审批人ID")
    private Long userIdOfAudit;

}
