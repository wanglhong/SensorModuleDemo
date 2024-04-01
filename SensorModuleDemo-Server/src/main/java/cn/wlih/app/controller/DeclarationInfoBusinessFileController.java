package cn.wlih.app.controller;

import cn.wlih.app.dto.DeclarationInfoBusinessFileDto;
import cn.wlih.app.model.DeclarationInfoBusinessFile;
import cn.wlih.app.service.DeclarationInfoBusinessFileService;
import cn.wlih.app.vo.DeclarationInfoBusinessFileVo;
import cn.wlih.core.base.controller.MyBaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Tag(name = "海关清关信息管理")
@RequestMapping("/api/app/declarationInfoBusinessFile")
public class DeclarationInfoBusinessFileController extends MyBaseController<DeclarationInfoBusinessFile, DeclarationInfoBusinessFileDto, DeclarationInfoBusinessFileVo> {

    @Autowired
    private DeclarationInfoBusinessFileService declarationInfoBusinessFileService;

}
