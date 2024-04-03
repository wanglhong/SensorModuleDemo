package cn.wlih.sensormodule.controller;

import cn.wlih.core.base.controller.MyBaseController;
import cn.wlih.sensormodule.dto.GpsInfoDto;
import cn.wlih.sensormodule.model.GpsInfo;
import cn.wlih.sensormodule.service.GpsInfoService;
import cn.wlih.sensormodule.vo.GpsInfoVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Tag(name = "GPS定位信息管理")
@RequestMapping("/api/sensormodule/gpsInfo")
public class GpsInfoController extends MyBaseController<GpsInfo, GpsInfoDto, GpsInfoVo> {

    @Autowired
    private GpsInfoService gpsInfoService;

}
