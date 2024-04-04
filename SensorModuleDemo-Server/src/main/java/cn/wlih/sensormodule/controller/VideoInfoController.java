package cn.wlih.sensormodule.controller;

import cn.wlih.core.base.controller.MyBaseController;
import cn.wlih.sensormodule.dto.VideoInfoDto;
import cn.wlih.sensormodule.model.VideoInfo;
import cn.wlih.sensormodule.service.VideoInfoService;
import cn.wlih.sensormodule.vo.VideoInfoVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Tag(name = "视频信息管理")
@RequestMapping("/api/sensormodule/videoInfo")
public class VideoInfoController extends MyBaseController<VideoInfo, VideoInfoDto, VideoInfoVo> {

    @Autowired
    private VideoInfoService videoInfoService;

}
