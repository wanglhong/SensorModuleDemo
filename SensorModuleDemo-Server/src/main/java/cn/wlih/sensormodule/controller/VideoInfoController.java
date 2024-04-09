package cn.wlih.sensormodule.controller;

import cn.wlih.core.base.controller.MyBaseController;
import cn.wlih.core.base.model.ResponseResult;
import cn.wlih.core.myAnnotate.MyRequestBody;
import cn.wlih.sensormodule.dto.VideoInfoDto;
import cn.wlih.sensormodule.model.VideoInfo;
import cn.wlih.sensormodule.service.VideoInfoService;
import cn.wlih.sensormodule.vo.VideoInfoVo;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Tag(name = "视频信息管理")
@RequestMapping("/api/sensormodule/videoInfo")
public class VideoInfoController extends MyBaseController<VideoInfo, VideoInfoDto, VideoInfoVo> {

    @Autowired
    private VideoInfoService videoInfoService;

    /**
     * 查看监控
     */
    @ApiOperationSupport(order = 6)
    @Operation(summary = "查看监控")
    @PostMapping("/viewMonitor")
    public ResponseResult<String> viewMonitor(@MyRequestBody Long transportInfoId) {
        if (transportInfoId == null) {
            return ResponseResult.error("运输信息ID不能为空");
        }
        // TODO 获取监控地址
        return ResponseResult.success("127.0.0.1:8084/hls/live_stream");
    }

}
