package cn.wlih.app.controller;

import cn.wlih.app.model.TransportEquipment;
import cn.wlih.app.service.TransportEquipmentService;
import cn.wlih.core.base.controller.MyBaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/app/transportEquipment")
public class TransportEquipmentController extends MyBaseController<TransportEquipment> {

    @Autowired
    private TransportEquipmentService transportEquipmentService;

}
