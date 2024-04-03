package cn.wlih.sensormodule.service.impl;

import cn.wlih.core.base.mapper.MyBaseMapper;
import cn.wlih.core.base.service.impl.MyBaseServiceImpl;
import cn.wlih.sensormodule.dao.GpsInfoMapper;
import cn.wlih.sensormodule.model.GpsInfo;
import cn.wlih.sensormodule.service.GpsInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service("gpsInfoService")
public class GpsInfoServiceImpl extends MyBaseServiceImpl<GpsInfo> implements GpsInfoService {

    @Autowired
    private GpsInfoMapper gpsInfoMapper;

    /**
     * @return 当前Service的主表Mapper对象
     */
    @Override
    protected MyBaseMapper<GpsInfo> mapper() {
        return gpsInfoMapper;
    }

}
