package cn.wlih.upms.service.impl;

import cn.wlih.core.base.mapper.MyBaseMapper;
import cn.wlih.core.base.service.impl.MyBaseServiceImpl;
import cn.wlih.upms.dao.SysOrganizationMapper;
import cn.wlih.upms.model.SysOrganization;
import cn.wlih.upms.service.SysOrganizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service("sysOrganizationService")
public class SysOrganizationServiceImpl extends MyBaseServiceImpl<SysOrganization> implements SysOrganizationService {

    @Autowired
    private SysOrganizationMapper sysOrganizationMapper;

    /**
     * @return 当前Service的主表Mapper对象
     */
    @Override
    protected MyBaseMapper<SysOrganization> mapper() {
        return sysOrganizationMapper;
    }

}
