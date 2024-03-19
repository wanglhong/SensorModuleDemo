package cn.wlih.app.controller;

import cn.wlih.app.dto.IotEquipmentDto;
import cn.wlih.app.model.IotEquipment;
import cn.wlih.app.service.IotEquipmentService;
import cn.wlih.app.vo.IotEquipmentVo;
import cn.wlih.core.base.controller.MyBaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Tag(name = "物联网设备管理")
@RequestMapping("/api/app/iotEquipment")
public class IotEquipmentController extends MyBaseController<IotEquipment, IotEquipmentDto, IotEquipmentVo> {

    @Autowired
    private IotEquipmentService iotEquipmentService;

}
