package cn.wlih.sensormodule.controller;

import cn.wlih.core.base.controller.MyBaseController;
import cn.wlih.core.base.model.ResponseResult;
import cn.wlih.core.myAnnotate.MyRequestBody;
import cn.wlih.sensormodule.dto.GpsInfoDto;
import cn.wlih.sensormodule.model.GpsInfo;
import cn.wlih.sensormodule.service.GpsInfoService;
import cn.wlih.sensormodule.vo.GpsInfoVo;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@Tag(name = "GPS定位信息管理")
@RequestMapping("/api/sensormodule/gpsInfo")
public class GpsInfoController extends MyBaseController<GpsInfo, GpsInfoDto, GpsInfoVo> {

    @Autowired
    private GpsInfoService gpsInfoService;

    /**
     * 获取运输路线
     */
    @ApiOperationSupport(order = 6)
    @Operation(summary = "获取运输路线")
    @PostMapping("/getTransportRoute")
    public ResponseResult<Map<String, List<Object>>> getTransportRoute(
            @Parameter(description = "运输信息ID") @MyRequestBody Long transportInfoId) {
        return ResponseResult.success(gpsInfoService.getTransportRoute(transportInfoId));
    }

}
