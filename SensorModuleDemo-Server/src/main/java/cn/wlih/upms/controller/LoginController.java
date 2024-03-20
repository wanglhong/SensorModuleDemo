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
        return ResponseResult.success(map);
    }

    @Operation(summary = "获取 menu")
    @GetMapping("/menu")
    public ResponseResult<List<Map>> getMenu() {
        String menu = "[]";
        List<Map> list = JSONUtil.toList(menu, Map.class);
        return ResponseResult.success(list);
    }

    @Operation(summary = "获取 permissions")
    @GetMapping("/permissions")
    public ResponseResult<List<String>> getPermissions() {
        String permissions = "[]";
        return ResponseResult.success(JSONUtil.toList(permissions, String.class));
    }

}
