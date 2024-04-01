package cn.wlih.app.controller;

import cn.wlih.app.dto.ClearanceInfoBusinessFileDto;
import cn.wlih.app.model.ClearanceInfoBusinessFile;
import cn.wlih.app.service.ClearanceInfoBusinessFileService;
import cn.wlih.app.vo.ClearanceInfoBusinessFileVo;
import cn.wlih.core.base.controller.MyBaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Tag(name = "海关清关信息管理")
@RequestMapping("/api/app/clearanceInfoBusinessFile")
public class ClearanceInfoBusinessFileController extends MyBaseController<ClearanceInfoBusinessFile, ClearanceInfoBusinessFileDto, ClearanceInfoBusinessFileVo> {

    @Autowired
    private ClearanceInfoBusinessFileService clearanceInfoBusinessFileService;

}
