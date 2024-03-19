package cn.wlih.upms.controller;

import cn.wlih.core.base.controller.MyBaseController;
import cn.wlih.upms.model.SysUser;
import cn.wlih.upms.service.SysUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统用户接口层
 */
@Slf4j
@RestController
@Tag(name = "系统用户管理")
@RequestMapping("/api/upms/user")
public class SysUserController extends MyBaseController<SysUser> {

    @Autowired
    private SysUserService sysUserService;

}
