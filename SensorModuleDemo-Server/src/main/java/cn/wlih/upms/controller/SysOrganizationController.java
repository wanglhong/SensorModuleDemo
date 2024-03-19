package cn.wlih.upms.controller;

import cn.wlih.core.base.controller.MyBaseController;
import cn.wlih.upms.model.SysOrganization;
import cn.wlih.upms.service.SysOrganizationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Tag(name = "系统组织管理")
@RequestMapping("/api/upms/organization")
public class SysOrganizationController extends MyBaseController<SysOrganization> {

    @Autowired
    private SysOrganizationService sysOrganizationService;

}
