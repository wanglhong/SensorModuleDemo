package cn.wlih.app.service.impl;

import cn.wlih.app.dao.CustomsClearanceInfoMapper;
import cn.wlih.app.model.CustomsClearanceInfo;
import cn.wlih.app.service.CustomsClearanceInfoService;
import cn.wlih.core.base.mapper.MyBaseMapper;
import cn.wlih.core.base.service.impl.MyBaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service("customsClearanceInfoService")
public class CustomsClearanceInfoServiceImpl extends MyBaseServiceImpl<CustomsClearanceInfo> implements CustomsClearanceInfoService {

    @Autowired
    private CustomsClearanceInfoMapper customsClearanceInfoMapper;

    /**
     * @return 当前Service的主表Mapper对象
     */
    @Override
    protected MyBaseMapper<CustomsClearanceInfo> mapper() {
        return customsClearanceInfoMapper;
    }

}
