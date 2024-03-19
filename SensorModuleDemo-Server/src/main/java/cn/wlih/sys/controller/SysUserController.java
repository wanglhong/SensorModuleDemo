package cn.wlih.sys.controller;

import cn.wlih.core.base.controller.MyBaseController;
import cn.wlih.sys.model.SysUser;
import cn.wlih.sys.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统用户接口层
 */
@Slf4j
@RestController
@RequestMapping("/app/user")
public class SysUserController extends MyBaseController<SysUser> {

    @Autowired
    private SysUserService sysUserService;

}
