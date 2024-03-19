package cn.wlih.app.controller;

import cn.wlih.app.model.TransportInfoTurnoverBoxGoods;
import cn.wlih.app.service.TransportInfoTurnoverBoxGoodsService;
import cn.wlih.core.base.controller.MyBaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Tag(name = "运输信息-周转箱-货物关联管理")
@RequestMapping("/api/app/transportInfoTurnoverBoxGoods")
public class TransportInfoTurnoverBoxGoodsController extends MyBaseController<TransportInfoTurnoverBoxGoods> {

    @Autowired
    private TransportInfoTurnoverBoxGoodsService transportInfoTurnoverBoxGoodsService;

}
