package cn.wlih.app.controller;

import cn.wlih.app.dto.BusinessFileDto;
import cn.wlih.app.model.BusinessFile;
import cn.wlih.app.service.BusinessFileService;
import cn.wlih.app.vo.BusinessFileVo;
import cn.wlih.core.base.controller.MyBaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Tag(name = "海关清关信息管理")
@RequestMapping("/api/app/businessFile")
public class BusinessFileController extends MyBaseController<BusinessFile, BusinessFileDto, BusinessFileVo> {

    @Autowired
    private BusinessFileService businessFileService;

}
