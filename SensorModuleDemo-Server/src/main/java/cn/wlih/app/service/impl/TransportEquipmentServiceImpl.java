package cn.wlih.app.service.impl;

import cn.wlih.app.dao.TransportEquipmentMapper;
import cn.wlih.app.model.TransportEquipment;
import cn.wlih.app.service.TransportEquipmentService;
import cn.wlih.core.base.mapper.MyBaseMapper;
import cn.wlih.core.base.service.impl.MyBaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service("transportEquipmentService")
public class TransportEquipmentServiceImpl extends MyBaseServiceImpl<TransportEquipment> implements TransportEquipmentService {

    @Autowired
    private TransportEquipmentMapper transportEquipmentMapper;

    /**
     * @return 当前Service的主表Mapper对象
     */
    @Override
    protected MyBaseMapper<TransportEquipment> mapper() {
        return transportEquipmentMapper;
    }

}
