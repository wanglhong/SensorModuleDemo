package cn.wlih.app.service.impl;

import cn.wlih.app.dao.IotEquipmentMapper;
import cn.wlih.app.model.IotEquipment;
import cn.wlih.app.service.IotEquipmentService;
import cn.wlih.core.base.mapper.MyBaseMapper;
import cn.wlih.core.base.service.impl.MyBaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service("iotEquipmentService")
public class IotEquipmentServiceImpl extends MyBaseServiceImpl<IotEquipment> implements IotEquipmentService {

    @Autowired
    private IotEquipmentMapper iotEquipmentMapper;

    /**
     * @return 当前Service的主表Mapper对象
     */
    @Override
    protected MyBaseMapper<IotEquipment> mapper() {
        return iotEquipmentMapper;
    }

}
