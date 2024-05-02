package cn.wlih.upms.controller;

import cn.hutool.json.JSONUtil;
import cn.wlih.core.base.model.ResponseResult;
import cn.wlih.core.myAnnotate.MyRequestBody;
import cn.wlih.upms.dto.SysUserDto;
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

    /**
     * TODO 登录
     * @param sysUserDto
     * @return
     */
    @Operation(summary = "登录接口")
    @PostMapping("/login")
    public ResponseResult<Map<String, Object>> login(@MyRequestBody SysUserDto sysUserDto) {
        if (sysUserDto == null || sysUserDto.getLoginName() == null) {
            return ResponseResult.error("登录失败，请输入用户名！");
        }
        if (sysUserDto.getPassword() == null) {
            return ResponseResult.error("登录失败，请输入密码！");
        }
        if (!"admin".equals(sysUserDto.getLoginName())) {
            return ResponseResult.error("登录失败，用户名不存在！");
        }
        if (!"123456".equals(sysUserDto.getPassword())) {
            return ResponseResult.error("登录失败，密码错误！");
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("userId", "35002");
        resultMap.put("token", "eyJhbGciOiJIUzUxMiJ9.eyJ1c2VySWQiOiJhZG1pbiIsInVzZXJOYW1lIjoiYWRtaW4iLCJvcmdDb2RlIjoiMzUwMDAiLCJkZXB0Q29kZSI6IjM1MDAwIiwiYXVkIjoiYWRtaW4iLCJpc3MiOiJhZG1pbiIsImV4cCI6MTU5MzUzNTU5OH0.0pJAojRtT5lx6PS2gH_Q9BmBxeNlgBL37ABX22HyDlebbr66cCjVYZ0v0zbLO_9241FX9-FZpCkEqE98MQOyWw");
        return ResponseResult.success(resultMap);
    }

/*
[
  {
    "id": "/workspace",
    "icon": "layui-icon-home",
    "title": "工作空间",
    "children": [
      {
        "id": "/workspace/inTransitGoodsMonitor",
        "icon": "layui-icon-util",
        "title": "在途货物监控"
      }
    ]
  },
  {
    "id": "/baseInfo",
    "icon": "layui-icon-home",
    "title": "基础信息管理",
    "children": [
      {
        "id": "/baseInfo/goods",
        "icon": "layui-icon-util",
        "title": "货物管理"
      },
      {
        "id": "/baseInfo/turnoverBox",
        "icon": "layui-icon-util",
        "title": "周转箱管理"
      },
      {
        "id": "/baseInfo/transportEquipment",
        "icon": "layui-icon-util",
        "title": "运输设备管理"
      },
      {
        "id": "/baseInfo/iotEquipment",
        "icon": "layui-icon-util",
        "title": "物联网设备管理"
      }
    ]
  },
  {
    "id": "/transportManagement",
    "icon": "layui-icon-unlink",
    "title": "运输管理",
    "children": [
      {
        "id": "/transportManagement/transportInfo",
        "icon": "layui-icon-not-found",
        "title": "运输信息管理"
      },
      {
        "id": "/transportManagement/goodsToTurnoverBox",
        "icon": "layui-icon-not-found",
        "title": "货物装箱"
      },
      {
        "id": "/transportManagement/urnoverBoxTotransportEquipment",
        "icon": "layui-icon-not-found",
        "title": "货物装车"
      }
    ]
  },
  {
    "id": "/customInfo",
    "icon": "layui-icon-unlink",
    "title": "通关信息管理",
    "children": [
      {
        "id": "/customInfo/customsDeclarationInfo",
        "icon": "layui-icon-not-found",
        "title": "报关信息管理"
      },
      {
        "id": "/customInfo/customsClearanceInfo",
        "icon": "layui-icon-not-found",
        "title": "清关信息管理"
      }
    ]
  },
  {
    "id": "/customApproval",
    "icon": "layui-icon-unlink",
    "title": "通关审批管理",
    "children": [
      {
        "id": "/customApproval/customsApproval",
        "icon": "layui-icon-not-found",
        "title": "通关审批管理"
      }
    ]
  }
]
*/
    @Operation(summary = "获取 menu")
    @GetMapping("/menu")
    public ResponseResult<List<Map>> getMenu() {
        String menu = "[{\"id\":\"/workspace\",\"icon\":\"layui-icon-home\",\"title\":\"工作空间\",\"children\":[{\"id\":\"/workspace/inTransitGoodsMonitor\",\"icon\":\"layui-icon-util\",\"title\":\"在途货物监控\"}]},{\"id\":\"/baseInfo\",\"icon\":\"layui-icon-home\",\"title\":\"基础信息管理\",\"children\":[{\"id\":\"/baseInfo/goods\",\"icon\":\"layui-icon-util\",\"title\":\"货物管理\"},{\"id\":\"/baseInfo/turnoverBox\",\"icon\":\"layui-icon-util\",\"title\":\"周转箱管理\"},{\"id\":\"/baseInfo/transportEquipment\",\"icon\":\"layui-icon-util\",\"title\":\"运输设备管理\"},{\"id\":\"/baseInfo/iotEquipment\",\"icon\":\"layui-icon-util\",\"title\":\"物联网设备管理\"}]},{\"id\":\"/transportManagement\",\"icon\":\"layui-icon-unlink\",\"title\":\"运输管理\",\"children\":[{\"id\":\"/transportManagement/transportInfo\",\"icon\":\"layui-icon-not-found\",\"title\":\"运输信息管理\"},{\"id\":\"/transportManagement/goodsToTurnoverBox\",\"icon\":\"layui-icon-not-found\",\"title\":\"货物装箱\"},{\"id\":\"/transportManagement/urnoverBoxTotransportEquipment\",\"icon\":\"layui-icon-not-found\",\"title\":\"货物装车\"}]},{\"id\":\"/customInfo\",\"icon\":\"layui-icon-unlink\",\"title\":\"通关信息管理\",\"children\":[{\"id\":\"/customInfo/customsDeclarationInfo\",\"icon\":\"layui-icon-not-found\",\"title\":\"报关信息管理\"},{\"id\":\"/customInfo/customsClearanceInfo\",\"icon\":\"layui-icon-not-found\",\"title\":\"清关信息管理\"}]},{\"id\":\"/customApproval\",\"icon\":\"layui-icon-unlink\",\"title\":\"通关审批管理\",\"children\":[{\"id\":\"/customApproval/customsApproval\",\"icon\":\"layui-icon-not-found\",\"title\":\"通关审批管理\"}]}]";
        List<Map> list = JSONUtil.toList(menu, Map.class);
        return ResponseResult.success(list);
    }

/*
[
  "sys:user:add",
  "sys:user:edit",
  "sys:user:delete",
  "sys:user:import",
  "sys:user:export"
]
*/
    @Operation(summary = "获取 permissions")
    @GetMapping("/permissions")
    public ResponseResult<List<String>> getPermissions() {
        String permissions = "[\"sys:user:add\",\"sys:user:edit\",\"sys:user:delete\",\"sys:user:import\",\"sys:user:export\"]";
        return ResponseResult.success(JSONUtil.toList(permissions, String.class));
    }

}
