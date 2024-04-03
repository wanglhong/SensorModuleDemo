package cn.wlih.app.service.impl;

import cn.wlih.app.dao.CustomsAuditMapper;
import cn.wlih.app.model.CustomsAudit;
import cn.wlih.app.service.CustomsAuditService;
import cn.wlih.core.base.mapper.MyBaseMapper;
import cn.wlih.core.base.service.impl.MyBaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service("customsAuditService")
public class CustomsAuditImpl extends MyBaseServiceImpl<CustomsAudit> implements CustomsAuditService {

    @Autowired
    private CustomsAuditMapper customsAuditMapper;

    /**
     * @return 当前Service的主表Mapper对象
     */
    @Override
    protected MyBaseMapper<CustomsAudit> mapper() {
        return customsAuditMapper;
    }

}
