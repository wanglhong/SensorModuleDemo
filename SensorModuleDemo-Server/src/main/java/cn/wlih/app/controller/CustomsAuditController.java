package cn.wlih.app.controller;

import cn.wlih.app.dto.CustomsAuditDto;
import cn.wlih.app.model.CustomsAudit;
import cn.wlih.app.service.CustomsAuditService;
import cn.wlih.app.vo.CustomsAuditVo;
import cn.wlih.core.base.controller.MyBaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Tag(name = "海关审核表管理")
@RequestMapping("/api/app/customsAudit")
public class CustomsAuditController extends MyBaseController<CustomsAudit, CustomsAuditDto, CustomsAuditVo> {

    @Autowired
    private CustomsAuditService customsAuditService;

}
