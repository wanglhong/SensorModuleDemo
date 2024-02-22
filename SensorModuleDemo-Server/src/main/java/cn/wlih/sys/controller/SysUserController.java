package cn.wlih.sys.controller;

import cn.wlih.core.base.controller.MyBaseController;
import cn.wlih.sys.model.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统用户接口层
 */
@Slf4j
@RestController()
@RequestMapping("/app/User")
public class SysUserController extends MyBaseController<SysUser> {
}
