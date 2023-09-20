package cn.wlih.sys.controller;

import cn.wlih.sys.model.SysUser;
import cn.wlih.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author 王立宏
 * 描述:
 * path: SensorModuleDemo-cn.wlih.sys.controller-TextController
 * date: 2023/9/20 14:14
 */
@RestController
public class TextController {

    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/test01")
    public String test01() {
        return "测试成功！";
    }

    @PostMapping("/test02")
    public List<SysUser> test02() {
        return sysUserService.selectAll();
    }

}
