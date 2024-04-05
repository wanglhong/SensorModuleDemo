package cn.wlih.sensormodule.controller;

import cn.wlih.core.base.controller.MyBaseController;
import cn.wlih.sensormodule.dto.UserProximityInfoDto;
import cn.wlih.sensormodule.model.UserProximityInfo;
import cn.wlih.sensormodule.service.UserProximityInfoService;
import cn.wlih.sensormodule.vo.UserProximityInfoVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Tag(name = "人物靠近信息管理")
@RequestMapping("/api/sensormodule/userProximityInfo")
public class UserProximityInfoController extends MyBaseController<UserProximityInfo, UserProximityInfoDto, UserProximityInfoVo> {

    @Autowired
    private UserProximityInfoService userProximityInfoService;

}
