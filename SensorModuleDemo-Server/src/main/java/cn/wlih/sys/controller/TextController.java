package cn.wlih.sys.controller;

import cn.wlih.sys.model.SysUser;
import cn.wlih.sys.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 描述:
 * @author 王立宏
 * @date 2023/9/20 14:14
 * @path SensorModuleDemo-cn.wlih.sys.controller-TextController
 */
@Slf4j
@RestController
public class TextController {

    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/")
    public String index() {
        return "SUCCESS！";
    }

    @GetMapping("/test01")
    public String test01() {
        MDC.put("user-id", null);
        log.error("Test");
        log.info("=============》test01《=============");
        return "测试成功！";
    }

    @GetMapping("/test02")
    public List<SysUser> test02() {
        log.info("=============》test02《=============");
        return sysUserService.selectAll();
    }

    @GetMapping("/enumTest")
    public SysUser enumTest() {
        SysUser sysUser = new SysUser();
        sysUser.setLoginName("小浣熊");
        sysUser.setPassword("密码001");
        sysUser.setPhoneNumber("18888888888");
        sysUser = sysUserService.saveNew(sysUser);
        return sysUser;
    }

}
