package cn.wlih.sys.controller;

import cn.wlih.sys.model.SysUser;
import cn.wlih.sys.service.SysUserService;
import cn.wlih.util.SshUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private SshUtil sshUtil;

    @GetMapping("/")
    public String index() {
        return "SUCCESS！";
    }

    @GetMapping("/test01")
    public String test01() {
        MDC.put("user-id", null);
        log.error("Test");
        log.info("=============> test01 <=============");
        return "测试成功！";
    }

    @GetMapping("/test02")
    public List<SysUser> test02() {
        log.info("=============> test02 <=============");
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

    @PostMapping(value = "/sshCommand", consumes = "application/json")
    @ResponseBody
    public String sshCommand(@RequestBody Map<String, List<String>> map) {
        List<String> commands = map.get("commands");
        return sshUtil.executeRemoteCommand(commands);
    }

    @PostMapping(value = "/uploadFile", consumes = "application/json")
    @ResponseBody
    public String uploadFile(@RequestBody Map<String, String> map) {
        if (map == null) {
            map = new HashMap<>();
            map.put("localFilePath", "./SensorModuleDemo-Client/target/SensorModuleDemoClient.jar");
            map.put("remotePath", "/home/pi/MyApps/SensorModuleDemoClient.jar");
        }
        String localFilePath = map.get("localFilePath");
        String remotePath = map.get("remotePath");
        return sshUtil.uploadFile(localFilePath, remotePath);
    }

    @PostMapping(value = "/downloadFile", consumes = "application/json")
    @ResponseBody
    public String downloadFile(@RequestBody Map<String, String> map) {
        if (map == null) {
            map.put("localFilePath", "./SysFile/hostFile/");
            map.put("remotePath", "");
        }
        String localFilePath = map.get("localFilePath");
        String remotePath = map.get("remotePath");
        return sshUtil.downloadFile(localFilePath, remotePath);
    }

    @PostMapping("/sshClose")
    @ResponseBody
    public String sshClose() {
        sshUtil.close();
        log.info("Close ssh success.");
        return "Close ssh success.";
    }

}
