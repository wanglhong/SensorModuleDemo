package cn.wlih.app.dao;

import cn.wlih.app.model.BusinessFile;
import cn.wlih.core.base.mapper.MyBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BusinessFileMapper extends MyBaseMapper<BusinessFile> {

    /**
     * 根据报关信息ID集合获取业务文件集合
     * @param declarationInfoIdList 报关信息ID集合
     * @return
     */
    List<BusinessFile> getBusinessFileListByDeclarationInfoIdList(@Param("declarationInfoIdList") List<Long> declarationInfoIdList);

    /**
     * 根据清关信息ID集合获取业务文件集合
     * @param clearanceInfoIdList 清关信息ID
     * @return
     */
    List<BusinessFile> getBusinessFileListByClearanceInfoIdList(@Param("clearanceInfoIdList") List<Long> clearanceInfoIdList);

}
