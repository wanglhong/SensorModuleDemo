package cn.wlih.app.service.impl;

import cn.wlih.app.dao.BusinessFileMapper;
import cn.wlih.app.dao.CustomsAuditMapper;
import cn.wlih.app.dao.CustomsClearanceInfoMapper;
import cn.wlih.app.dao.DeclarationInfoBusinessFileMapper;
import cn.wlih.app.model.*;
import cn.wlih.app.service.CustomsAuditService;
import cn.wlih.app.vo.BusinessFileVo;
import cn.wlih.core.base.mapper.MyBaseMapper;
import cn.wlih.core.base.service.impl.MyBaseServiceImpl;
import cn.wlih.core.util.MyModelUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service("customsAuditService")
public class CustomsAuditImpl extends MyBaseServiceImpl<CustomsAudit> implements CustomsAuditService {

    @Autowired
    private CustomsAuditMapper customsAuditMapper;
    @Autowired
    private BusinessFileMapper businessFileMapper;

    /**
     * @return 当前Service的主表Mapper对象
     */
    @Override
    protected MyBaseMapper<CustomsAudit> mapper() {
        return customsAuditMapper;
    }

    /**
     * 查询所有数据
     * TODO 待逻辑优化
     * @param customsAudit
     * @return 返回查询结果
     */
    @Override
    public List<CustomsAudit> selectList(CustomsAudit customsAudit) {
        List<CustomsAudit> customsAuditList = super.selectList(customsAudit);
        for (CustomsAudit audit : customsAuditList) {
            Long declarationInfoId = audit.getCustomsDeclarationInfoId();
            if (declarationInfoId != null) {
                List<BusinessFile> businessFileListByDeclarationInfoIdList = businessFileMapper.getBusinessFileListByDeclarationInfoIdList(Collections.singletonList(declarationInfoId));
                List<BusinessFileVo> businessFileListOfDeclarationInfo = new ArrayList<>();
                for (BusinessFile businessFile : businessFileListByDeclarationInfoIdList) {
                    businessFileListOfDeclarationInfo.add(MyModelUtil.copyTo(businessFile, BusinessFileVo.class));
                }
                audit.setBusinessFileList(businessFileListOfDeclarationInfo);
            }

            Long clearanceInfoId = audit.getCustomsClearanceInfoId();
            if (clearanceInfoId != null) {
                List<BusinessFile> businessFileListByClearanceInfoIdList = businessFileMapper.getBusinessFileListByClearanceInfoIdList(Collections.singletonList(clearanceInfoId));
                List<BusinessFileVo> businessFileListOfClearanceInfo = new ArrayList<>();
                for (BusinessFile businessFile : businessFileListByClearanceInfoIdList) {
                    businessFileListOfClearanceInfo.add(MyModelUtil.copyTo(businessFile, BusinessFileVo.class));
                }
                audit.setBusinessFileList(businessFileListOfClearanceInfo);
            }
        }
        return customsAuditList;
    }

}
