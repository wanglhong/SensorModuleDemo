package cn.wlih.app.service.impl;

import cn.wlih.app.dao.ClearanceInfoBusinessFileMapper;
import cn.wlih.app.model.ClearanceInfoBusinessFile;
import cn.wlih.app.service.ClearanceInfoBusinessFileService;
import cn.wlih.core.base.mapper.MyBaseMapper;
import cn.wlih.core.base.service.impl.MyBaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service("clearanceInfoBusinessFileService")
public class ClearanceInfoBusinessFileServiceImpl extends MyBaseServiceImpl<ClearanceInfoBusinessFile> implements ClearanceInfoBusinessFileService {

    @Autowired
    private ClearanceInfoBusinessFileMapper clearanceInfoBusinessFileMapper;

    /**
     * @return 当前Service的主表Mapper对象
     */
    @Override
    protected MyBaseMapper<ClearanceInfoBusinessFile> mapper() {
        return clearanceInfoBusinessFileMapper;
    }

}
