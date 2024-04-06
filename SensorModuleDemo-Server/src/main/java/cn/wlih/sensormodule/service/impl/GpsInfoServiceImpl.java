package cn.wlih.sensormodule.service.impl;

import cn.wlih.core.base.mapper.MyBaseMapper;
import cn.wlih.core.base.service.impl.MyBaseServiceImpl;
import cn.wlih.core.object.TokenData;
import cn.wlih.sensormodule.dao.GpsInfoMapper;
import cn.wlih.sensormodule.model.GpsInfo;
import cn.wlih.sensormodule.service.GpsInfoService;
import cn.wlih.websocket.service.ISocketIOService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service("gpsInfoService")
public class GpsInfoServiceImpl extends MyBaseServiceImpl<GpsInfo> implements GpsInfoService {

    @Autowired
    private GpsInfoMapper gpsInfoMapper;
    @Autowired
    private ISocketIOService socketIOService;

    /**
     * @return 当前Service的主表Mapper对象
     */
    @Override
    protected MyBaseMapper<GpsInfo> mapper() {
        return gpsInfoMapper;
    }

    /**
     * 获取运输路线
     *
     * @param transportInfoId 运输信息ID
     * @return {"center":[], "latlngs":[[],[]]}
     */
    @Override
    public Map<String, List<List<Object>>> getTransportRoute(Long transportInfoId) {
        List<GpsInfo> gpsInfoList = gpsInfoMapper.selectList(new LambdaQueryWrapper<GpsInfo>()
                .eq(GpsInfo::getTransportInfoId, transportInfoId)
                .orderBy(true, true, GpsInfo::getCreateTime));
        List<List<Object>> center = new LinkedList<>();
        List<List<Object>> latlngs = new LinkedList<>();
        List<Object> latlng = new LinkedList<>();
        for (GpsInfo gpsInfo : gpsInfoList) {
            latlng = new LinkedList<>();
            latlng.add(gpsInfo.getLongitude());
            latlng.add(gpsInfo.getLatitude());
            latlngs.add(latlng);
        }
        center.add(latlng);
        Map<String, List<List<Object>>> resultData = new HashMap<>();
        resultData.put("center", center);
        resultData.put("latlngs", latlngs);
        return resultData;
    }

    /**
     * WebSocket获取运输路线
     *
     * @param transportInfoId 运输信息ID
     * @param serialNumber    信息序号
     */
    @Override
    public Map<String, List<List<Object>>> getTransportRouteOfWebSocket(Long transportInfoId, Integer serialNumber) {
        List<GpsInfo> gpsInfoList = gpsInfoMapper.selectList(new LambdaQueryWrapper<GpsInfo>()
                .eq(GpsInfo::getTransportInfoId, transportInfoId)
                .gt(GpsInfo::getSerialNumber, serialNumber)
                .orderBy(true, true, GpsInfo::getSerialNumber));
        if (gpsInfoList.isEmpty()) {
            return null;
        }
        List<List<Object>> center = new LinkedList<>();
        List<List<Object>> latlngs = new LinkedList<>();
        List<Object> latlng = new LinkedList<>();
        for (GpsInfo gpsInfo : gpsInfoList) {
            latlng = new LinkedList<>();
            latlng.add(gpsInfo.getLongitude());
            latlng.add(gpsInfo.getLatitude());
            latlngs.add(latlng);
        }
        center.add(latlng);
        Map<String, List<List<Object>>> resultData = new HashMap<>();
        resultData.put("center", center);
        resultData.put("latlngs", latlngs);
        return resultData;
    }

}
