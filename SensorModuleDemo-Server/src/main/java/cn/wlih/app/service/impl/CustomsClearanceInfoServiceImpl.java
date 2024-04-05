package cn.wlih.app.service.impl;

import cn.wlih.app.dao.BusinessFileMapper;
import cn.wlih.app.dao.ClearanceInfoBusinessFileMapper;
import cn.wlih.app.dao.CustomsClearanceInfoMapper;
import cn.wlih.app.model.BusinessFile;
import cn.wlih.app.model.ClearanceInfoBusinessFile;
import cn.wlih.app.model.CustomsClearanceInfo;
import cn.wlih.app.service.CustomsClearanceInfoService;
import cn.wlih.app.vo.BusinessFileVo;
import cn.wlih.core.base.mapper.MyBaseMapper;
import cn.wlih.core.base.service.impl.MyBaseServiceImpl;
import cn.wlih.core.util.MyModelUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service("customsClearanceInfoService")
public class CustomsClearanceInfoServiceImpl extends MyBaseServiceImpl<CustomsClearanceInfo> implements CustomsClearanceInfoService {

    @Autowired
    private CustomsClearanceInfoMapper customsClearanceInfoMapper;
    @Autowired
    private ClearanceInfoBusinessFileMapper clearanceInfoBusinessFileMapper;
    @Autowired
    private BusinessFileMapper businessFileMapper;

    /**
     * @return 当前Service的主表Mapper对象
     */
    @Override
    protected MyBaseMapper<CustomsClearanceInfo> mapper() {
        return customsClearanceInfoMapper;
    }

    /**
     * 查询所有数据
     *
     * @param customsClearanceInfo
     * @return 返回查询结果
     */
    @Override
    public List<CustomsClearanceInfo> selectList(CustomsClearanceInfo customsClearanceInfo) {
        List<CustomsClearanceInfo> customsClearanceInfoList = super.selectList(customsClearanceInfo);
        if (customsClearanceInfoList.isEmpty()) {
            return customsClearanceInfoList;
        }
        List<Long> clearanceInfoIdList = new ArrayList<>();
        for (CustomsClearanceInfo clearanceInfo : customsClearanceInfoList) {
            clearanceInfoIdList.add(clearanceInfo.getId());
        }
        List<ClearanceInfoBusinessFile> clearanceInfoBusinessFileList = clearanceInfoBusinessFileMapper.selectList(
                new LambdaQueryWrapper<ClearanceInfoBusinessFile>().in(ClearanceInfoBusinessFile::getClearanceInfoId, clearanceInfoIdList));
        List<Long> businessFileIdList = new ArrayList<>();
        Map<Long, List<Long>> clearanceInfoIdToBusinessFileIdListMap = new HashMap<>();
        for (ClearanceInfoBusinessFile clearanceInfoBusinessFile : clearanceInfoBusinessFileList) {
            Long businessFileId = clearanceInfoBusinessFile.getBusinessFileId();
            Long clearanceInfoId = clearanceInfoBusinessFile.getClearanceInfoId();
            if (businessFileId == null && clearanceInfoId == null) {
                continue;
            }
            businessFileIdList.add(businessFileId);
            if (!clearanceInfoIdToBusinessFileIdListMap.containsKey(clearanceInfoId)) {
                clearanceInfoIdToBusinessFileIdListMap.put(clearanceInfoId, new ArrayList<>());
            }
            clearanceInfoIdToBusinessFileIdListMap.get(clearanceInfoId).add(businessFileId);
        }
        List<BusinessFile> businessFileList = businessFileMapper.selectList(
                new LambdaQueryWrapper<BusinessFile>().in(!businessFileIdList.isEmpty(), BusinessFile::getId, businessFileIdList));
        Map<Long, BusinessFile> businessFileIdToBusinessFileMap = new HashMap<>();
        for (BusinessFile businessFile : businessFileList) {
            businessFileIdToBusinessFileMap.put(businessFile.getId(), businessFile);
        }
        for (CustomsClearanceInfo clearanceInfo : customsClearanceInfoList) {
            Long clearanceInfoId = clearanceInfo.getId();
            if (!clearanceInfoIdToBusinessFileIdListMap.containsKey(clearanceInfoId)) {
                continue;
            }
            List<Long> businessFileIds = clearanceInfoIdToBusinessFileIdListMap.get(clearanceInfoId);
            if (businessFileIds.size() == 0) {
                continue;
            }
            List<BusinessFileVo> businessFileVoList = new ArrayList<>();
            for (Long businessFileId : businessFileIds) {
                if (businessFileIdToBusinessFileMap.containsKey(businessFileId)) {
                    BusinessFile businessFile = businessFileIdToBusinessFileMap.get(businessFileId);
                    businessFileVoList.add(MyModelUtil.copyTo(businessFile, BusinessFileVo.class));
                }
            }
            clearanceInfo.setBusinessFileList(businessFileVoList);
        }
        return customsClearanceInfoList;
    }

}
