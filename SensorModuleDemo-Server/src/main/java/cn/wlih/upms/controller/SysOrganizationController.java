package cn.wlih.upms.controller;

import cn.wlih.core.base.controller.MyBaseController;
import cn.wlih.core.base.model.ResponseResult;
import cn.wlih.upms.dto.SysOrganizationDto;
import cn.wlih.upms.model.SysOrganization;
import cn.wlih.upms.service.SysOrganizationService;
import cn.wlih.upms.vo.SysOrganizationVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@Tag(name = "系统组织管理")
@RequestMapping("/api/upms/organization")
public class SysOrganizationController extends MyBaseController<SysOrganization, SysOrganizationDto, SysOrganizationVo> {

    @Autowired
    private SysOrganizationService sysOrganizationService;

    /**
     * TODO 获取运输人选择树
     */
    @GetMapping("/getUserTree")
    public ResponseResult<List<Map<String, Object>>> getUserTree() {
        return ResponseResult.success(sysOrganizationService.getUserTree(Collections.singletonList(1001L)));
    }

    /**
     * TODO 运输工具选择树
     */
    @GetMapping("/getEquipmentTree")
    public ResponseResult<List<Map<String, Object>>> getEquipmentTree() {
        return ResponseResult.success(sysOrganizationService.getEquipmentTree(Collections.singletonList(1001L)));
    }

}
