package cn.wlih.app.service.impl;

import cn.wlih.app.dao.CustomsDeclarationInfoMapper;
import cn.wlih.app.model.CustomsDeclarationInfo;
import cn.wlih.app.service.CustomsDeclarationInfoService;
import cn.wlih.core.base.mapper.MyBaseMapper;
import cn.wlih.core.base.service.impl.MyBaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service("customsDeclarationInfoService")
public class CustomsDeclarationInfoServiceImpl extends MyBaseServiceImpl<CustomsDeclarationInfo> implements CustomsDeclarationInfoService {

    @Autowired
    private CustomsDeclarationInfoMapper customsDeclarationInfoMapper;

    /**
     * @return 当前Service的主表Mapper对象
     */
    @Override
    protected MyBaseMapper<CustomsDeclarationInfo> mapper() {
        return customsDeclarationInfoMapper;
    }

}
