package cn.wlih.app.controller;

import cn.wlih.app.dto.CustomsAuditDto;
import cn.wlih.app.model.CustomsAudit;
import cn.wlih.app.service.CustomsAuditService;
import cn.wlih.app.vo.CustomsAuditVo;
import cn.wlih.core.base.controller.MyBaseController;
import cn.wlih.core.base.model.ResponseResult;
import cn.wlih.core.myAnnotate.MyRequestBody;
import cn.wlih.core.object.TokenData;
import cn.wlih.core.util.MyModelUtil;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
        customsAudit = customsAuditService.add(customsAudit);
        return ResponseResult.success(MyModelUtil.copyTo(customsAudit, CustomsAuditVo.class));
    }
}
