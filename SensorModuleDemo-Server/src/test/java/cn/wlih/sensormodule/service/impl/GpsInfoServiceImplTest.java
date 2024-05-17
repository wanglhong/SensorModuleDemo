package cn.wlih.sensormodule.service.impl;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.json.JSONUtil;
import cn.wlih.core.base.model.modelDbEnum.IsDeleteEnum;
import cn.wlih.core.sequence.wrapper.IdGeneratorWrapper;
import cn.wlih.sensormodule.dao.GpsInfoMapper;
import cn.wlih.sensormodule.model.GpsInfo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
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
    void getTransportRoute() throws IOException {
//        Long transportInfoId = 1775743480383934464L;
//        Long transportInfoId = 1775783992247717888L;
//        Long transportInfoId = 1775796839505727488L;
//        Long transportInfoId = 1775808128189730816L;
//        Long userId = 1001L;
//        List<List> list = JSONUtil.toList(JSONStr, List.class);
//        getTransportRoute2();
//        list = Collections.singletonList(coorListAll);
//        Integer serialNumber = 1;
//        for (List list1 : list) {
//            GpsInfo gpsInfo = new GpsInfo();
//            gpsInfo.setSerialNumber(serialNumber);
//            gpsInfo.setTransportInfoId(transportInfoId);
//            gpsInfo.setLongitude(new BigDecimal(list1.get(0).toString()));
//            gpsInfo.setLatitude(new BigDecimal(list1.get(1).toString()));
//
//            gpsInfo.setId(idGeneratorWrapper.nextLongId());
//            gpsInfo.setCreateUserId(userId);
//            gpsInfo.setUpdateUserId(userId);
//            gpsInfo.setCreateTime(new Date());
//            gpsInfo.setUpdateTime(new Date());
//            gpsInfoMapper.insert(gpsInfo);
//            serialNumber++;
//            ThreadUtil.sleep(1000);
//        }

        Long transportInfoId = 1775743480383934464L;
        Long userId = 1001L;
        getTransportRoute2();
        List<List<BigDecimal>> gpsList = getGPSListByFile();
        Integer serialNumber = 1;
        for (List<BigDecimal> gpsDataList : gpsList) {
            GpsInfo gpsInfo = new GpsInfo();
            gpsInfo.setIsDelete(IsDeleteEnum.EXIST);
            gpsInfo.setSerialNumber(serialNumber);
            gpsInfo.setTransportInfoId(transportInfoId);
            gpsInfo.setLongitude(gpsDataList.get(1));
            gpsInfo.setLatitude(gpsDataList.get(0));

            gpsInfo.setId(idGeneratorWrapper.nextLongId());
            gpsInfo.setCreateUserId(userId);
            gpsInfo.setUpdateUserId(userId);
            gpsInfo.setCreateTime(new Date());
            gpsInfo.setUpdateTime(new Date());
            gpsInfoMapper.insert(gpsInfo);
            serialNumber++;
            ThreadUtil.sleep(50);
        }
    }

    private static final String JSON_STR_A = "";
    private static final String JSON_STR_B = "";
    private static final String JSON_STR_C = "";

    @Test
    void getTransportRoute2() throws IOException {
        List<List<BigDecimal>> coorListAll = getGPSListByFile();
        System.out.println("===============================================");
        System.out.println(JSONUtil.toJsonStr(coorListAll));
        System.out.println("===============================================");
    }

    private List<List<BigDecimal>> getGPSListByFile() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
//        JsonNode pathJsonNodeListA = mapper.readTree(JSON_STR_A);
        File jsonFile = new File("D:\\wlih\\各类文件\\大四第一学期\\毕业设计（实现部分）\\项目代码\\SensorModuleDemo\\SensorModuleDevFile\\MapJson.json");
        JsonNode pathJsonNodeListA = mapper.readTree(jsonFile);
        List<String> coorList = new LinkedList<>();
        for (JsonNode pathJsonNodeA : pathJsonNodeListA) {
            JsonNode pathJsonNodeList = pathJsonNodeA.get("path");
            for (JsonNode pathJsonNode : pathJsonNodeList) {
                JsonNode segmentsJsonNodeList = pathJsonNode.get("segments");
                for (JsonNode segmentsJsonNode : segmentsJsonNodeList) {
                    String coor = segmentsJsonNode.get("coor").toString();
                    coorList.add(coor);
                }
            }
        }
//        System.out.println("====================================");
//        System.out.println(JSONUtil.toJsonStr(coorList));
//        System.out.println("====================================\n\n");
        List<List<BigDecimal>> coorListAll = new LinkedList<>();
        for (String coor : coorList) {
            coor = coor.replace("\"", "");
            List<BigDecimal> acoorList = JSONUtil.toList(coor, BigDecimal.class);
            int acoorListSize = acoorList.size();
            if (!isPowerOfTwo(acoorListSize)) {
                throw new RuntimeException("【" + coor + "】不为2的倍数！");
            }
            List<BigDecimal> coorListA = null;
            for (int i = 1; i <= acoorListSize; i++) {
                if (isPowerOfTwo(i)) {
                    // 为2的倍数
                    coorListA.add(acoorList.get(i-1));
                    coorListAll.add(coorListA);
                } else {
                    // 不为2的倍数
                    coorListA = new LinkedList<>();
                    coorListA.add(acoorList.get(i-1));
                }
            }
        }
        return coorListAll;
    }

    /**
     * 是否为 2 的倍数
     * @param number
     * @return
     */
    public boolean isPowerOfTwo(int number) {
        return (number & 1) == 0;
    }

}