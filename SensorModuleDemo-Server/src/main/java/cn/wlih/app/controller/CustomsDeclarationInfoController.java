package cn.wlih.app.controller;

import cn.wlih.app.model.CustomsDeclarationInfo;
import cn.wlih.app.service.CustomsDeclarationInfoService;
import cn.wlih.core.base.controller.MyBaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Tag(name = "报关清关信息管理")
@RequestMapping("/api/app/customsClearanceInfo")
public class CustomsDeclarationInfoController extends MyBaseController<CustomsDeclarationInfo> {

    @Autowired
    private CustomsDeclarationInfoService customsDeclarationInfoService;

}
