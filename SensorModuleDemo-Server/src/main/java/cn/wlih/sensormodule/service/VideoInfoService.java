package cn.wlih.sensormodule.service;

import cn.wlih.core.base.service.MyBaseService;
import cn.wlih.sensormodule.model.VideoInfo;

public interface VideoInfoService extends MyBaseService<VideoInfo> {

    /**
     * 查看监控
     * @param transportInfoId 运输信息ID
     * @return
     */
    String viewMonitor(Long transportInfoId);

}
