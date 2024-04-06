package cn.wlih.sensormodule.service.impl;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.json.JSONUtil;
import cn.wlih.core.sequence.wrapper.IdGeneratorWrapper;
import cn.wlih.sensormodule.dao.GpsInfoMapper;
import cn.wlih.sensormodule.model.GpsInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Slf4j
@SpringBootTest
class GpsInfoServiceImplTest {

    @Autowired
    private IdGeneratorWrapper idGeneratorWrapper;
    @Autowired
    private GpsInfoMapper gpsInfoMapper;

    private static final String JSONStr = "[\n" +
            "        [39.898457, 116.391844],\n" +
            "        [39.898595, 116.377947],\n" +
            "        [39.898341, 116.368001],\n" +
            "        [39.898063, 116.357144],\n" +
            "        [39.899095, 116.351934],\n" +
            "        [39.905871, 116.350670],\n" +
            "        [39.922329, 116.349800],\n" +
            "        [39.931017, 116.349671],\n" +
            "        [39.939104, 116.349225],\n" +
            "        [39.942233, 116.349910],\n" +
            "        [39.947263, 116.366892],\n" +
            "        [39.947568, 116.387537],\n" +
            "        [39.947764, 116.401988],\n" +
            "        [39.947929, 116.410824],\n" +
            "        [39.947558, 116.426740],\n" +
            "        [39.939700, 116.427338],\n" +
            "        [39.932404, 116.427919],\n" +
            "        [39.923109, 116.428377],\n" +
            "        [39.907094, 116.429583],\n" +
            "        [39.906858, 116.414040],\n" +
            "        [39.906622, 116.405321],\n" +
            "        [39.906324, 116.394954],\n" +
            "        [39.906308, 116.391264],\n" +
            "        [39.916611, 116.390748]\n" +
            "    ]";

    @Test
    void getTransportRoute() {
//        Long transportInfoId = 1775743480383934464L;
//        Long transportInfoId = 1775783992247717888L;
//        Long transportInfoId = 1775796839505727488L;
        Long transportInfoId = 1775808128189730816L;
        Long userId = 1001L;
        List<List> list = JSONUtil.toList(JSONStr, List.class);
        Integer serialNumber = 1;
        for (List list1 : list) {
            GpsInfo gpsInfo = new GpsInfo();
            gpsInfo.setSerialNumber(serialNumber);
            gpsInfo.setTransportInfoId(transportInfoId);
            gpsInfo.setLongitude(new BigDecimal(list1.get(0).toString()));
            gpsInfo.setLatitude(new BigDecimal(list1.get(1).toString()));

            gpsInfo.setId(idGeneratorWrapper.nextLongId());
            gpsInfo.setCreateUserId(userId);
            gpsInfo.setUpdateUserId(userId);
            gpsInfo.setCreateTime(new Date());
            gpsInfo.setUpdateTime(new Date());
            gpsInfoMapper.insert(gpsInfo);
            serialNumber++;
            ThreadUtil.sleep(1000);
        }
    }

}