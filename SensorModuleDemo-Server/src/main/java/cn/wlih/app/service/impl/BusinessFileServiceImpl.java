package cn.wlih.app.service.impl;

import cn.wlih.app.dao.BusinessFileMapper;
import cn.wlih.app.model.BusinessFile;
import cn.wlih.app.service.BusinessFileService;
import cn.wlih.core.base.mapper.MyBaseMapper;
import cn.wlih.core.base.service.impl.MyBaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service("businessFileService")
public class BusinessFileServiceImpl extends MyBaseServiceImpl<BusinessFile> implements BusinessFileService {

    @Autowired
    private BusinessFileMapper businessFileMapper;

    /**
     * @return 当前Service的主表Mapper对象
     */
    @Override
    protected MyBaseMapper<BusinessFile> mapper() {
        return businessFileMapper;
    }

}
