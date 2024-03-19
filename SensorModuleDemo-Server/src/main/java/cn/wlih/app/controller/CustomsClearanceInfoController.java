package cn.wlih.app.controller;

import cn.wlih.app.model.CustomsClearanceInfo;
import cn.wlih.app.service.CustomsClearanceInfoService;
import cn.wlih.core.base.controller.MyBaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/app/customsClearanceInfo")
public class CustomsClearanceInfoController extends MyBaseController<CustomsClearanceInfo> {

    @Autowired
    private CustomsClearanceInfoService customsClearanceInfoService;

}
