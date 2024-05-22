package cn.wlih.sensormodule.service.impl;

import cn.wlih.core.base.mapper.MyBaseMapper;
import cn.wlih.core.base.service.impl.MyBaseServiceImpl;
import cn.wlih.sensormodule.dao.VideoInfoMapper;
import cn.wlih.sensormodule.model.VideoInfo;
import cn.wlih.sensormodule.service.VideoInfoService;
import cn.wlih.websocket.service.SocketIOService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service("videoInfoService")
public class VideoInfoServiceImpl extends MyBaseServiceImpl<VideoInfo> implements VideoInfoService {

    @Autowired
    private VideoInfoMapper videoInfoMapper;
    @Resource
    private SocketIOService socketIOService;

    /**
     * @return 当前Service的主表Mapper对象
     */
    @Override
    protected MyBaseMapper<VideoInfo> mapper() {
        return videoInfoMapper;
    }

    /**
     * 查看监控
     *
     * @param transportInfoId 运输信息ID
     * @return
     */
    @Override
    public String viewMonitor(Long transportInfoId) {
        Long iotEquipmentId = 1774067734078820351L;
        socketIOService.viewMonitor(iotEquipmentId, transportInfoId);
        // 系统休眠3秒
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return "http://wlih.cn:8082/hls/" + transportInfoId + ".m3u8";
    }
}
