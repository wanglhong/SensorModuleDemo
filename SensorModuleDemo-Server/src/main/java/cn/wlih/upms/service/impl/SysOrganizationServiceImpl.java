package cn.wlih.upms.service.impl;

import cn.hutool.json.JSONUtil;
import cn.wlih.core.base.mapper.MyBaseMapper;
import cn.wlih.core.base.service.impl.MyBaseServiceImpl;
import cn.wlih.upms.dao.SysOrganizationMapper;
import cn.wlih.upms.model.SysOrganization;
import cn.wlih.upms.service.SysOrganizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 获取用户树
     *
     * @param organizationIdList 公司（组织）ID
     * @return 用户树
     */
    @Override
    public List<Map<String, Object>> getUserTree(List<Long> organizationIdList) {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("id", 10001L);
        userMap.put("type", "USER");
        userMap.put("name", "测试员工01");
        Map<String, Object> deptMap = new HashMap<>();
        deptMap.put("id", 20001L);
        deptMap.put("type", "DEPT");
        deptMap.put("disabled", true);
        deptMap.put("name", "测试部门01");
        deptMap.put("children", new ArrayList<>().add(userMap));
        Map<String, Object> organization = new HashMap<>();
        organization.put("id", 3001L);
        organization.put("type", "ORGANIZATION");
        organization.put("disabled", true);
        organization.put("name", "测试公司01");
        organization.put("children", new ArrayList<>().add(deptMap));
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(organization);
        return list;
    }

    /**
     * 获取用户树
     *
     * @param organizationIdList 公司（组织）ID
     * @return 用户树
     */
    @Override
    public List<Map<String, Object>> getEquipmentTree(List<Long> organizationIdList) {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("id", 10001L);
        userMap.put("type", "EQUIPMENT");
        userMap.put("name", "运输工具01");
        Map<String, Object> deptMap = new HashMap<>();
        deptMap.put("id", 20001L);
        deptMap.put("type", "DEPT");
        deptMap.put("disabled", true);
        deptMap.put("name", "测试部门01");
        deptMap.put("children", new ArrayList<>().add(userMap));
        Map<String, Object> organization = new HashMap<>();
        organization.put("id", 3001L);
        organization.put("type", "ORGANIZATION");
        organization.put("disabled", true);
        organization.put("name", "测试公司01");
        organization.put("children", new ArrayList<>().add(deptMap));
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(organization);
        return list;
    }

}
