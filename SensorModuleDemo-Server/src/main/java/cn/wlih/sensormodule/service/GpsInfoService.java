package cn.wlih.sensormodule.service;

import cn.wlih.core.base.service.MyBaseService;
import cn.wlih.sensormodule.model.GpsInfo;

import java.util.List;
import java.util.Map;

public interface GpsInfoService extends MyBaseService<GpsInfo> {

    /**
     * 获取运输路线
     * @param transportInfoId 运输信息ID
     * @return {"center":[], "":[[],[]]}
     */
    Map<String, List<Object>> getTransportRoute(Long transportInfoId);

}
