package cn.wlih.sys.controller;

import cn.wlih.core.base.controller.MyBaseController;
import cn.wlih.sys.model.SysOrganization;
import cn.wlih.sys.service.SysOrganizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/app/organization")
public class SysOrganizationController extends MyBaseController<SysOrganization> {

    @Autowired
    private SysOrganizationService sysOrganizationService;

}
