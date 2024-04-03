package cn.wlih.upms.service.impl;

import cn.hutool.json.JSONUtil;
import cn.wlih.app.dao.TransportEquipmentMapper;
import cn.wlih.app.model.TransportEquipment;
import cn.wlih.core.base.mapper.MyBaseMapper;
import cn.wlih.core.base.service.impl.MyBaseServiceImpl;
import cn.wlih.upms.dao.SysDeptMapper;
import cn.wlih.upms.dao.SysOrganizationMapper;
import cn.wlih.upms.dao.SysUserMapper;
import cn.wlih.upms.model.SysDept;
import cn.wlih.upms.model.SysOrganization;
import cn.wlih.upms.model.SysUser;
import cn.wlih.upms.service.SysOrganizationService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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

    @Autowired
    private SysDeptMapper sysDeptMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private TransportEquipmentMapper transportEquipmentMapper;

    /**
     * 获取用户树
     *
     * @param organizationIdList 公司（组织）ID
     * @return 用户树
     */
    @Override
    public List<Map<String, Object>> getUserTree(List<Long> organizationIdList) {
        List<SysOrganization> organizationList = sysOrganizationMapper.selectList(new LambdaQueryWrapper<SysOrganization>().in(SysOrganization::getId, organizationIdList));
        if (organizationList.isEmpty()) {
            return new ArrayList<>();
        }

        List<SysDept> deptList = sysDeptMapper.selectList(new LambdaQueryWrapper<SysDept>().in(SysDept::getOrganizationId, organizationIdList));
        if (deptList.isEmpty()) {
            return new ArrayList<>();
        }
        List<Long> deptIdList = new ArrayList<>();
        Map<Long, List<Long>> organizationIdDeptIdListMap = new HashMap<>();
        Map<Long, Map<String, Object>> deptListMap = new HashMap<>();
        for (SysDept dept : deptList) {
            if (!organizationIdList.contains(dept.getOrganizationId())) {
                continue;
            }
            Map<String, Object> deptMap = new HashMap<>();
            deptMap.put("id", dept.getId());
            deptMap.put("type", "DEPT");
            deptMap.put("disabled", true);
            deptMap.put("name", dept.getDeptName());
            deptMap.put("children", new ArrayList<>());
            deptListMap.put(dept.getId(), deptMap);
            deptIdList.add(dept.getId());
            if (!organizationIdDeptIdListMap.containsKey(dept.getOrganizationId())) {
                organizationIdDeptIdListMap.put(dept.getOrganizationId(), new ArrayList<>());
            }
            organizationIdDeptIdListMap.get(dept.getOrganizationId()).add(dept.getId());
        }
        // 用户数据处理
        List<SysUser> userList = sysUserMapper.selectList(new LambdaQueryWrapper<SysUser>().in(SysUser::getDeptId, deptIdList));
        Map<Long, Map<String, Object>> userListMap = new HashMap<>();
        for (SysUser user : userList) {
            if (!deptListMap.containsKey(user.getDeptId())) {
                continue;
            }
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("id", user.getId());
            userMap.put("type", "USER");
            userMap.put("disabled", false);
            userMap.put("name", user.getUserName());
            ((List) deptListMap.get(user.getDeptId()).get("children")).add(userMap);
        }

        Map<Long, List<Map<String, Object>>> organizationDeptListMap = new HashMap<>();
        organizationIdDeptIdListMap.forEach((key, value) -> {
            List<Map<String, Object>> deptMapList = new ArrayList<>();
            for (Long deptId : value) {
                deptMapList.add(deptListMap.get(deptId));
            }
            organizationDeptListMap.put(key, deptMapList);
        });

        List<Map<String, Object>> resultDataList = new ArrayList<>();
        for (SysOrganization organization : organizationList) {
            Map<String, Object> organizationMap = new HashMap<>();
            organizationMap.put("id", organization.getId());
            organizationMap.put("type", "ORGANIZATION");
            organizationMap.put("disabled", true);
            organizationMap.put("name", organization.getOrganizationName());
            organizationMap.put("children", organizationDeptListMap.get(organization.getId()));
            resultDataList.add(organizationMap);
        }
        return resultDataList;
    }

    /**
     * 获取用户树
     *
     * @param organizationIdList 公司（组织）ID
     * @return 用户树
     */
    @Override
    public List<Map<String, Object>> getEquipmentTree(List<Long> organizationIdList) {
        List<SysOrganization> organizationList = sysOrganizationMapper.selectList(new LambdaQueryWrapper<SysOrganization>().in(SysOrganization::getId, organizationIdList));
        if (organizationList.isEmpty()) {
            return new ArrayList<>();
        }

        List<SysDept> deptList = sysDeptMapper.selectList(new LambdaQueryWrapper<SysDept>().in(SysDept::getOrganizationId, organizationIdList));
        if (deptList.isEmpty()) {
            return new ArrayList<>();
        }
        List<Long> deptIdList = new ArrayList<>();
        Map<Long, List<Long>> organizationIdDeptIdListMap = new HashMap<>();
        Map<Long, Map<String, Object>> deptListMap = new HashMap<>();
        for (SysDept dept : deptList) {
            if (!organizationIdList.contains(dept.getOrganizationId())) {
                continue;
            }
            Map<String, Object> deptMap = new HashMap<>();
            deptMap.put("id", dept.getId());
            deptMap.put("type", "DEPT");
            deptMap.put("disabled", true);
            deptMap.put("name", dept.getDeptName());
            deptMap.put("children", new ArrayList<>());
            deptListMap.put(dept.getId(), deptMap);
            deptIdList.add(dept.getId());
            if (!organizationIdDeptIdListMap.containsKey(dept.getOrganizationId())) {
                organizationIdDeptIdListMap.put(dept.getOrganizationId(), new ArrayList<>());
            }
            organizationIdDeptIdListMap.get(dept.getOrganizationId()).add(dept.getId());
        }
        // 用户数据处理
        List<TransportEquipment> transportEquipmentList = transportEquipmentMapper.selectList(new LambdaQueryWrapper<TransportEquipment>().in(TransportEquipment::getDataDeptId, deptIdList));
        Map<Long, Map<String, Object>> userListMap = new HashMap<>();
        for (TransportEquipment transportEquipment : transportEquipmentList) {
            if (!deptListMap.containsKey(transportEquipment.getDataDeptId())) {
                continue;
            }
            Map<String, Object> transportEquipmentMap = new HashMap<>();
            transportEquipmentMap.put("id", transportEquipment.getId());
            transportEquipmentMap.put("type", "TRANSPORT_EQUIPOMENT");
            transportEquipmentMap.put("disabled", false);
            transportEquipmentMap.put("name", transportEquipment.getEquipmentCode());
            ((List) deptListMap.get(transportEquipment.getDataDeptId()).get("children")).add(transportEquipmentMap);
        }

        Map<Long, List<Map<String, Object>>> organizationDeptListMap = new HashMap<>();
        organizationIdDeptIdListMap.forEach((key, value) -> {
            List<Map<String, Object>> deptMapList = new ArrayList<>();
            for (Long deptId : value) {
                deptMapList.add(deptListMap.get(deptId));
            }
            organizationDeptListMap.put(key, deptMapList);
        });

        List<Map<String, Object>> resultDataList = new ArrayList<>();
        for (SysOrganization organization : organizationList) {
            Map<String, Object> organizationMap = new HashMap<>();
            organizationMap.put("id", organization.getId());
            organizationMap.put("type", "ORGANIZATION");
            organizationMap.put("disabled", true);
            organizationMap.put("name", organization.getOrganizationName());
            organizationMap.put("children", organizationDeptListMap.get(organization.getId()));
            resultDataList.add(organizationMap);
        }
        return resultDataList;
    }

}
