package cn.wlih.app.service.impl;

import cn.wlih.app.dao.TransportInfoMapper;
import cn.wlih.app.model.TransportInfo;
import cn.wlih.app.service.TransportInfoService;
import cn.wlih.core.base.mapper.MyBaseMapper;
import cn.wlih.core.base.service.impl.MyBaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service("transportInfoService")
public class TransportInfoServiceImpl extends MyBaseServiceImpl<TransportInfo> implements TransportInfoService {

    @Autowired
    private TransportInfoMapper transportInfoMapper;

    /**
     * @return 当前Service的主表Mapper对象
     */
    @Override
    protected MyBaseMapper<TransportInfo> mapper() {
        return transportInfoMapper;
    }

}
