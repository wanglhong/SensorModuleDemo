package cn.wlih.app.service;

import cn.wlih.app.model.CustomsDeclarationInfo;
import cn.wlih.core.base.service.MyBaseService;

public interface CustomsDeclarationInfoService extends MyBaseService<CustomsDeclarationInfo> {

    /**
     * 通过运输信息ID查询清关信息
     * @param transportInfoId 运输信息ID
     * @return
     */
    CustomsDeclarationInfo viewByTransportInfo(Long transportInfoId);

}
