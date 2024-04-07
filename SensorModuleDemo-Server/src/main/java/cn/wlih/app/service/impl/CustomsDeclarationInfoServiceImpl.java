package cn.wlih.app.service.impl;

import cn.wlih.app.dao.BusinessFileMapper;
import cn.wlih.app.dao.CustomsDeclarationInfoMapper;
import cn.wlih.app.dao.DeclarationInfoBusinessFileMapper;
import cn.wlih.app.model.*;
import cn.wlih.app.service.CustomsDeclarationInfoService;
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
@Service("customsDeclarationInfoService")
public class CustomsDeclarationInfoServiceImpl extends MyBaseServiceImpl<CustomsDeclarationInfo> implements CustomsDeclarationInfoService {

    @Autowired
    private CustomsDeclarationInfoMapper customsDeclarationInfoMapper;
    @Autowired
    private DeclarationInfoBusinessFileMapper declarationInfoBusinessFileMapper;
    @Autowired
    private BusinessFileMapper businessFileMapper;

    /**
     * @return 当前Service的主表Mapper对象
     */
    @Override
    protected MyBaseMapper<CustomsDeclarationInfo> mapper() {
        return customsDeclarationInfoMapper;
    }

    /**
     * 查询所有数据
     *
     * @param customsDeclarationInfo
     * @return 返回查询结果
     */
    @Override
    public List<CustomsDeclarationInfo> selectList(CustomsDeclarationInfo customsDeclarationInfo) {
        List<CustomsDeclarationInfo> customsDeclarationInfoList = super.selectList(customsDeclarationInfo);
        if (customsDeclarationInfoList.isEmpty()) {
            return customsDeclarationInfoList;
        }
        this.verifyImportBussissFile(customsDeclarationInfoList);
        return customsDeclarationInfoList;
    }

    private void verifyImportBussissFile(List<CustomsDeclarationInfo> customsDeclarationInfoList) {
        List<Long> declarationInfoIdList = new ArrayList<>();
        for (CustomsDeclarationInfo declarationInfo : customsDeclarationInfoList) {
            declarationInfoIdList.add(declarationInfo.getId());
        }
        List<DeclarationInfoBusinessFile> declarationInfoBusinessFileList = declarationInfoBusinessFileMapper.selectList(
                new LambdaQueryWrapper<DeclarationInfoBusinessFile>().in(DeclarationInfoBusinessFile::getDeclarationInfoId, declarationInfoIdList));
        List<Long> businessFileIdList = new ArrayList<>();
        Map<Long, List<Long>> declarationInfoIdToBusinessFileIdListMap = new HashMap<>();
        for (DeclarationInfoBusinessFile declarationInfoBusinessFile : declarationInfoBusinessFileList) {
            Long businessFileId = declarationInfoBusinessFile.getBusinessFileId();
            Long declarationInfoId = declarationInfoBusinessFile.getDeclarationInfoId();
            if (businessFileId == null && declarationInfoId == null) {
                continue;
            }
            businessFileIdList.add(businessFileId);
            if (!declarationInfoIdToBusinessFileIdListMap.containsKey(declarationInfoId)) {
                declarationInfoIdToBusinessFileIdListMap.put(declarationInfoId, new ArrayList<>());
            }
            declarationInfoIdToBusinessFileIdListMap.get(declarationInfoId).add(businessFileId);
        }
        List<BusinessFile> businessFileList = businessFileMapper.selectList(
                new LambdaQueryWrapper<BusinessFile>().in(!businessFileIdList.isEmpty(), BusinessFile::getId, businessFileIdList));
        Map<Long, BusinessFile> businessFileIdToBusinessFileMap = new HashMap<>();
        for (BusinessFile businessFile : businessFileList) {
            businessFileIdToBusinessFileMap.put(businessFile.getId(), businessFile);
        }
        for (CustomsDeclarationInfo declarationInfo : customsDeclarationInfoList) {
            Long declarationInfoId = declarationInfo.getId();
            if (!declarationInfoIdToBusinessFileIdListMap.containsKey(declarationInfoId)) {
                continue;
            }
            List<Long> businessFileIds = declarationInfoIdToBusinessFileIdListMap.get(declarationInfoId);
            if (businessFileIds.isEmpty()) {
                continue;
            }
            List<BusinessFileVo> businessFileVoList = new ArrayList<>();
            for (Long businessFileId : businessFileIds) {
                if (businessFileIdToBusinessFileMap.containsKey(businessFileId)) {
                    BusinessFile businessFile = businessFileIdToBusinessFileMap.get(businessFileId);
                    businessFileVoList.add(MyModelUtil.copyTo(businessFile, BusinessFileVo.class));
                }
            }
            declarationInfo.setBusinessFileList(businessFileVoList);
        }
    }

    /**
     * 通过运输信息ID查询清关信息
     *
     * @param transportInfoId 运输信息ID
     * @return
     */
    @Override
    public CustomsDeclarationInfo viewByTransportInfo(Long transportInfoId) {
        CustomsDeclarationInfo customsDeclarationInfo = customsDeclarationInfoMapper.selectOne(new LambdaQueryWrapper<CustomsDeclarationInfo>()
                .eq(CustomsDeclarationInfo::getTransportInfoId, transportInfoId));
        if (customsDeclarationInfo == null) {
            return null;
        }
        this.verifyImportForOneToOneRelation(customsDeclarationInfo);
        List<CustomsDeclarationInfo> customsDeclarationInfoList = new ArrayList<>();
        customsDeclarationInfoList.add(customsDeclarationInfo);
        this.verifyImportBussissFile(customsDeclarationInfoList);
        return customsDeclarationInfoList.get(0);
    }

}
