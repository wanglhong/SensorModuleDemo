package cn.wlih.app.service.impl;

import cn.wlih.app.dao.DeclarationInfoBusinessFileMapper;
import cn.wlih.app.model.DeclarationInfoBusinessFile;
import cn.wlih.app.service.DeclarationInfoBusinessFileService;
import cn.wlih.core.base.mapper.MyBaseMapper;
import cn.wlih.core.base.service.impl.MyBaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service("declarationInfoBusinessFileService")
public class DeclarationInfoBusinessFileServiceImpl extends MyBaseServiceImpl<DeclarationInfoBusinessFile> implements DeclarationInfoBusinessFileService {

    @Autowired
    private DeclarationInfoBusinessFileMapper declarationInfoBusinessFileMapper;

    /**
     * @return 当前Service的主表Mapper对象
     */
    @Override
    protected MyBaseMapper<DeclarationInfoBusinessFile> mapper() {
        return declarationInfoBusinessFileMapper;
    }

}
