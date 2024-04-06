package cn.wlih.app.controller;

import cn.wlih.app.dto.CustomsAuditDto;
import cn.wlih.app.model.CustomsAudit;
import cn.wlih.app.model.modelDbEnum.AuditState;
import cn.wlih.app.service.CustomsAuditService;
import cn.wlih.app.vo.CustomsAuditVo;
import cn.wlih.core.base.controller.MyBaseController;
import cn.wlih.core.base.model.ResponseResult;
import cn.wlih.core.myAnnotate.MyRequestBody;
import cn.wlih.core.object.TokenData;
import cn.wlih.core.util.MyModelUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Tag(name = "海关审核表管理")
@RequestMapping("/api/app/customsAudit")
public class CustomsAuditController extends MyBaseController<CustomsAudit, CustomsAuditDto, CustomsAuditVo> {

    @Autowired
    private CustomsAuditService customsAuditService;

    /**
     * 基础新增接口
     *
     * @param modelDto 实体
     */
    @Override
    public ResponseResult<CustomsAuditVo> add(
            @Parameter(description = "新增的对象信息") @MyRequestBody CustomsAuditDto modelDto) {
        CustomsAudit customsAudit = MyModelUtil.copyTo(modelDto, CustomsAudit.class);
        customsAudit.setOrganizationIdOfSubmit(TokenData.takeFromRequest().getOrganizationId());
        customsAudit.setDeptIdOfSubmit(TokenData.takeFromRequest().getDeptId());
        customsAudit.setUserIdOfSubmit(TokenData.takeFromRequest().getUserId());
        customsAudit.setAuditState(AuditState.WAIT_AUDIT);
        customsAudit = customsAuditService.add(customsAudit);
        return ResponseResult.success(MyModelUtil.copyTo(customsAudit, CustomsAuditVo.class));
    }

    /**
     * 提交审核信息
     */
    @ApiOperationSupport(order = 6)
    @Operation(summary = "提交审核信息")
    @PostMapping("/submitAudit")
    public ResponseResult<Void> submitAudit(
            @Parameter(description = "审批信息ID") @MyRequestBody Long id) {
        return auditStateUpdate(id, AuditState.AUDITING);
    }

    /**
     * 撤回审核信息
     */
    @ApiOperationSupport(order = 7)
    @Operation(summary = "撤回审核")
    @PostMapping("/revokeAudit")
    public ResponseResult<Void> revokeAudit(
            @Parameter(description = "审批信息ID") @MyRequestBody Long id) {
        return auditStateUpdate(id, AuditState.WAIT_AUDIT);
    }

    /**
     * 审核通过
     */
    @ApiOperationSupport(order = 8)
    @Operation(summary = "审核通过")
    @PostMapping("/auditPass")
    public ResponseResult<Void> auditPass(
            @Parameter(description = "审批信息ID") @MyRequestBody Long id) {
        return auditStateUpdate(id, AuditState.AUDIT_PASS);
    }

    /**
     * 审核驳回
     */
    @ApiOperationSupport(order = 9)
    @Operation(summary = "审核驳回")
    @PostMapping("/auditReject")
    public ResponseResult<Void> auditReject(
            @Parameter(description = "审批信息ID") @MyRequestBody Long id) {
        return auditStateUpdate(id, AuditState.AUDIT_REJECT);
    }

    private ResponseResult<Void> auditStateUpdate(Long id, AuditState auditState) {
        if (id == null) {
            return ResponseResult.error("审批信息ID不能为空");
        }
        CustomsAudit customsAudit = customsAuditService.getById(id);
        if (customsAudit == null) {
            return ResponseResult.error("审批信息不存在，无法操作！");
        }
        if (customsAuditService.update(new LambdaUpdateWrapper<CustomsAudit>()
                .ge(CustomsAudit::getId, id).set(CustomsAudit::getAuditState, auditState))) {
            return ResponseResult.success();
        }
        return ResponseResult.error();
    }

}
