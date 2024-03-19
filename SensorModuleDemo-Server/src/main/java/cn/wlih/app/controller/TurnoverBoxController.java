package cn.wlih.app.controller;

import cn.wlih.app.model.TurnoverBox;
import cn.wlih.app.service.TurnoverBoxService;
import cn.wlih.core.base.controller.MyBaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Tag(name = "周转箱管理")
@RequestMapping("/api/app/turnoverBox")
public class TurnoverBoxController extends MyBaseController<TurnoverBox> {

    @Autowired
    private TurnoverBoxService turnoverBoxService;

}
