package cn.wlih.app.controller;

import cn.wlih.app.dto.TransportInfoDto;
import cn.wlih.app.model.TransportInfo;
import cn.wlih.app.service.TransportInfoService;
import cn.wlih.app.vo.TransportInfoVo;
import cn.wlih.core.base.controller.MyBaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Tag(name = "运输信息管理")
@RequestMapping("/api/app/transportInfo")
public class TransportInfoController extends MyBaseController<TransportInfo, TransportInfoDto, TransportInfoVo> {

    @Autowired
    private TransportInfoService transportInfoService;

}
