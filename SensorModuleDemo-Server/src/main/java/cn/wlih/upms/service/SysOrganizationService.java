package cn.wlih.upms.service;

import cn.wlih.core.base.service.MyBaseService;
import cn.wlih.upms.model.SysOrganization;

import java.util.List;
import java.util.Map;

public interface SysOrganizationService extends MyBaseService<SysOrganization> {

    /**
     * 获取用户树
     * @param organizationId 公司（组织）ID
     * @return 用户树
     */
    List<Map<String, Object>> getUserTree(Long organizationId);

}
