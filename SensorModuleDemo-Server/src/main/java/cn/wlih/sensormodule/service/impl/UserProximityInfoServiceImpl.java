package cn.wlih.sensormodule.service.impl;

import cn.wlih.core.base.mapper.MyBaseMapper;
import cn.wlih.core.base.service.impl.MyBaseServiceImpl;
import cn.wlih.sensormodule.dao.UserProximityInfoMapper;
import cn.wlih.sensormodule.model.UserProximityInfo;
import cn.wlih.sensormodule.service.UserProximityInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service("userProximityInfoService")
public class UserProximityInfoServiceImpl extends MyBaseServiceImpl<UserProximityInfo> implements UserProximityInfoService {

    @Autowired
    private UserProximityInfoMapper userProximityInfoMapper;

    /**
     * @return 当前Service的主表Mapper对象
     */
    @Override
    protected MyBaseMapper<UserProximityInfo> mapper() {
        return userProximityInfoMapper;
    }

}
