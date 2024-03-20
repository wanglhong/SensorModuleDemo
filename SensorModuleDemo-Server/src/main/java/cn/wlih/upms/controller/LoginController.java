package cn.wlih.upms.controller;

import cn.hutool.json.JSONUtil;
import cn.wlih.core.base.model.ResponseResult;
import cn.wlih.upms.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@Tag(name = "系统权限管理")
@RequestMapping("/api/upms/sys")
public class LoginController {

    @Autowired
    private SysUserService sysUserService;

    @Operation(summary = "登录接口")
    @PostMapping("/login")
    public ResponseResult<Object> login(@RequestBody Map<String, Object> map) {
//        String loginName, String password, String vercode;
        map.put("token", "aaaaaaaaaaaaaaaaa");
        return ResponseResult.success(map);
    }

    @Operation(summary = "获取 menu")
    @GetMapping("/menu")
    public ResponseResult<List<Map>> getMenu() {
        String menu = "[{\"id\":\"/workspace\",\"icon\":\"layui-icon-home\",\"title\":\"工作空间\",\"children\":[{\"id\":\"/workspace/workbench\",\"icon\":\"layui-icon-util\",\"title\":\"工作台\"},{\"id\":\"/workspace/console\",\"icon\":\"layui-icon-engine\",\"title\":\"控制台\"},{\"id\":\"/workspace/analysis\",\"icon\":\"layui-icon-chart-screen\",\"title\":\"分析页\"},{\"id\":\"/workspace/monitor\",\"icon\":\"layui-icon-find-fill\",\"title\":\"监控页\"}]},{\"id\":\"/form\",\"icon\":\"layui-icon-table\",\"title\":\"表单页面\",\"children\":[{\"id\":\"/form/base\",\"icon\":\"layui-icon-form\",\"title\":\"基础表单\"},{\"id\":\"/form/intricate\",\"icon\":\"layui-icon-form\",\"title\":\"复杂表单\"},{\"id\":\"/form/step\",\"icon\":\"layui-icon-form\",\"title\":\"分步表单\"}]},{\"id\":\"/table\",\"icon\":\"layui-icon-align-left\",\"title\":\"列表页面\",\"children\":[{\"id\":\"/table/base\",\"icon\":\"layui-icon-search\",\"title\":\"查询列表\"},{\"id\":\"/table/card\",\"icon\":\"layui-icon-file-b\",\"title\":\"卡片列表\"},{\"id\":\"/table/project\",\"icon\":\"layui-icon-templeate-one\",\"title\":\"项目列表\"},{\"id\":\"/table/article\",\"icon\":\"layui-icon-carousel\",\"title\":\"文章列表\"}]},{\"id\":\"/result\",\"icon\":\"layui-icon-template\",\"title\":\"结果页面\",\"children\":[{\"id\":\"/result/success\",\"icon\":\"layui-icon-success\",\"title\":\"成功页面\"},{\"id\":\"/result/failure\",\"icon\":\"layui-icon-error\",\"title\":\"失败页面\"}]},{\"id\":\"/error\",\"icon\":\"layui-icon-unlink\",\"title\":\"异常页面\",\"children\":[{\"id\":\"/error/403\",\"icon\":\"layui-icon-not-found\",\"title\":\"403\"},{\"id\":\"/error/404\",\"icon\":\"layui-icon-not-found\",\"title\":\"404\"},{\"id\":\"/error/500\",\"icon\":\"layui-icon-not-found\",\"title\":\"500\"}]},{\"id\":\"/menu\",\"icon\":\"layui-icon-app\",\"title\":\"菜单嵌套\",\"children\":[{\"id\":\"/menu/menu1\",\"icon\":\"layui-icon-component\",\"title\":\"二级菜单\",\"children\":[{\"id\":\"/menu/menu1/menu1\",\"icon\":\"layui-icon-template-one\",\"title\":\"三级菜单\"},{\"id\":\"/menu/menu1/menu2\",\"icon\":\"layui-icon-template-one\",\"title\":\"三级菜单\"}]},{\"id\":\"/menu/menu2\",\"icon\":\"layui-icon-component\",\"title\":\"二级菜单\",\"children\":[{\"id\":\"/menu/menu2/menu1\",\"icon\":\"layui-icon-template-one\",\"title\":\"三级菜单\"},{\"id\":\"/menu/menu2/menu2\",\"icon\":\"layui-icon-template-one\",\"title\":\"三级菜单\"}]}]},{\"id\":\"/directive\",\"icon\":\"layui-icon-test\",\"title\":\"内置指令\",\"children\":[{\"id\":\"/directive/permission\",\"icon\":\"layui-icon-template\",\"title\":\"权限指令\"}]},{\"id\":\"/page\",\"icon\":\"layui-icon-link\",\"title\":\"外链页面\",\"children\":[{\"id\":\"layui-icon-layer\",\"icon\":\"layui-icon-home\",\"title\":\"弹层外链\",\"type\":\"modal\"},{\"id\":\"http://www.baidu.com\",\"icon\":\"layui-icon-layouts\",\"title\":\"原生跳转\",\"type\":\"blank\"}]},{\"id\":\"/enrollee\",\"icon\":\"layui-icon-slider\",\"title\":\"个人中心\",\"children\":[{\"id\":\"/enrollee/profile\",\"icon\":\"layui-icon-username\",\"title\":\"我的资料\"},{\"id\":\"/enrollee/message\",\"icon\":\"layui-icon-email\",\"title\":\"我的消息\"}]},{\"id\":\"/system\",\"icon\":\"layui-icon-set\",\"title\":\"系统管理\",\"children\":[{\"id\":\"/system/user\",\"icon\":\"layui-icon-user\",\"title\":\"用户管理\"},{\"id\":\"/system/role\",\"icon\":\"layui-icon-user\",\"title\":\"角色管理\"},{\"id\":\"/system/menu\",\"icon\":\"layui-icon-spread-left\",\"title\":\"菜单管理\"},{\"id\":\"/system/organization\",\"icon\":\"layui-icon-transfer\",\"title\":\"机构管理\"},{\"id\":\"/system/dictionary\",\"icon\":\"layui-icon-read\",\"title\":\"字典管理\"},{\"id\":\"/system/file\",\"icon\":\"layui-icon-file\",\"title\":\"文件管理\"},{\"id\":\"/system/login\",\"icon\":\"layui-icon-date\",\"title\":\"登录日志\"},{\"id\":\"/system/option\",\"icon\":\"layui-icon-survey\",\"title\":\"操作日志\"}]}]";
        List<Map> list = JSONUtil.toList(menu, Map.class);
        return ResponseResult.success(list);
    }

    @Operation(summary = "获取 permissions")
    @GetMapping("/permissions")
    public ResponseResult<List<String>> getPermissions() {
        String permissions = "[\"sys:user:add\",\"sys:user:edit\",\"sys:user:delete\",\"sys:user:import\",\"sys:user:export\"]";
        return ResponseResult.success(JSONUtil.toList(permissions, String.class));
    }

}
