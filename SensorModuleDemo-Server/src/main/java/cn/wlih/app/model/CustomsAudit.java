package cn.wlih.app.model;

import cn.wlih.app.model.modelDbEnum.AuditState;
import cn.wlih.app.model.modelDbEnum.AuditType;
import cn.wlih.core.base.model.BaseModel;
import cn.wlih.core.myAnnotate.ClassComment;
import cn.wlih.core.myAnnotate.RelationOneToOne;
import cn.wlih.core.myAnnotate.VariableComment;
import cn.wlih.upms.model.SysDept;
import cn.wlih.upms.model.SysOrganization;
import cn.wlih.upms.model.SysUser;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("sm_customs_audit")
@ClassComment("海关审核表")
@EqualsAndHashCode(callSuper = true)
public class CustomsAudit extends BaseModel {

    @VariableComment("审批名称")
    private String auditName;

    @VariableComment("审批提交组织ID")
    private Long organizationIdOfSubmit;

    @VariableComment("审批提交部门ID")
    private Long deptIdOfSubmit;

    @VariableComment("审批提交人ID")
    private Long userIdOfSubmit;

    @VariableComment("审批类型")
    private AuditType auditType;

    @VariableComment("报关信息ID")
    private Long customsDeclarationInfoId;

    @VariableComment("清关信息ID")
    private Long customsClearanceInfoId;

    @VariableComment("审批状态")
    private AuditState auditState;

    @VariableComment("备注")
    private String remark;

    @VariableComment("审批组织ID")
    private Long organizationIdOfAudit;

    @VariableComment("审批部门ID")
    private Long deptIdOfAudit;

    @VariableComment("审批人ID")
    private Long userIdOfAudit;

    @VariableComment("审批提交组织信息")
    @TableField(exist = false)
    @RelationOneToOne(
            masterIdField = "organizationIdOfSubmit",
            slaveIdField = "id",
            slaveModelClass = SysOrganization.class,
            slaveServiceName = "sysOrganizationService"
    )
    private SysOrganization organizationOfSubmit;

    @VariableComment("审批提交部门信息")
    @TableField(exist = false)
    @RelationOneToOne(
            masterIdField = "deptIdOfSubmit",
            slaveIdField = "id",
            slaveModelClass = SysDept.class,
            slaveServiceName = "sysDeptService"
    )
    private SysDept deptOfSubmit;

    @VariableComment("审批提交人信息")
    @TableField(exist = false)
    @RelationOneToOne(
            masterIdField = "userIdOfSubmit",
            slaveIdField = "id",
            slaveModelClass = SysUser.class,
            slaveServiceName = "sysUserService"
    )
    private SysUser userOfSubmit;

    @VariableComment("报关信息")
    @TableField(exist = false)
    @RelationOneToOne(
            masterIdField = "customsDeclarationInfoId",
            slaveIdField = "id",
            slaveModelClass = CustomsDeclarationInfo.class,
            slaveServiceName = "customsDeclarationInfoService"
    )
    private CustomsDeclarationInfo customsDeclarationInfo;

    @VariableComment("清关信息")
    @TableField(exist = false)
    @RelationOneToOne(
            masterIdField = "customsClearanceInfoId",
            slaveIdField = "id",
            slaveModelClass = CustomsClearanceInfo.class,
            slaveServiceName = "customsClearanceInfoService"
    )
    private CustomsClearanceInfo customsClearanceInfo;

    @VariableComment("审批组织信息")
    @TableField(exist = false)
    @RelationOneToOne(
            masterIdField = "organizationIdOfAudit",
            slaveIdField = "id",
            slaveModelClass = SysOrganization.class,
            slaveServiceName = "sysOrganizationService"
    )
    private SysOrganization organizationOfAudit;

    @VariableComment("审批部门信息")
    @TableField(exist = false)
    @RelationOneToOne(
            masterIdField = "deptIdOfAudit",
            slaveIdField = "id",
            slaveModelClass = SysDept.class,
            slaveServiceName = "sysDeptService"
    )
    private SysDept deptOfAudit;

    @VariableComment("审批人信息")
    @TableField(exist = false)
    @RelationOneToOne(
            masterIdField = "userIdOfAudit",
            slaveIdField = "id",
            slaveModelClass = SysUser.class,
            slaveServiceName = "sysUserService"
    )
    private SysUser userOfAudit;

}
