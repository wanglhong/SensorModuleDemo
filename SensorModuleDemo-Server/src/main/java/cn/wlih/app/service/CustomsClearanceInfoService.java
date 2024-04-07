package cn.wlih.app.service;

import cn.wlih.app.model.CustomsClearanceInfo;
import cn.wlih.core.base.service.MyBaseService;

public interface CustomsClearanceInfoService extends MyBaseService<CustomsClearanceInfo> {

    /**
     * 通过运输信息ID查询清关信息
     * @param transportInfoId 运输信息ID
     * @return
     */
    CustomsClearanceInfo viewByTransportInfo(Long transportInfoId);

}
