package cn.wlih.websocket.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.wlih.app.model.TransportInfo;
import cn.wlih.app.model.modelDbEnum.TransportState;
import cn.wlih.app.service.TransportInfoService;
import cn.wlih.core.base.model.ResponseResult;
import cn.wlih.core.myError.BizException;
import cn.wlih.sensormodule.service.GpsInfoService;
import cn.wlih.websocket.config.ServiceEvent;
import cn.wlih.websocket.config.ParamsKeyEnum;
import cn.wlih.websocket.service.SocketIOService;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service(value = "socketIOService")
public class SocketIOServiceImpl implements SocketIOService {

    /**
     * 存放已连接的客户端
     */
    private static Map<Long, SocketIOClient> clientMap = new ConcurrentHashMap<>();

    @Autowired
    private SocketIOServer socketIOServer;
    @Autowired
    private GpsInfoService gpsInfoService;
    @Autowired
    private TransportInfoService transportInfoService;

    /**
     * Spring IoC容器创建之后，在加载SocketIOServiceImpl Bean之后启动
     */
    @PostConstruct
    private void autoStartup() {
        start();
    }

    /**
     * Spring IoC容器在销毁SocketIOServiceImpl Bean之前关闭,避免重启项目服务端口占用问题
     */
    @PreDestroy
    private void autoStop() {
        stop();
    }

    @Override
    public void start() {
        // 监听客户端连接
        socketIOServer.addConnectListener(client -> {
            log.info("===============> 客户端 [" + getIpByClient(client) + "] 已连接 <===============");
            // 自定义事件`connected` -> 与客户端通信  （也可以使用内置事件，如：Socket.EVENT_CONNECT）
            client.sendEvent(ServiceEvent.CONNECTED_EVENT, "CONNECT_SUCCESS");
            String id = getParamsByKey(client, ParamsKeyEnum.ID);
            if (id != null) {
                clientMap.put(Long.valueOf(id), client);
            }
        });
        // 监听客户端断开连接
        socketIOServer.addDisconnectListener(client -> {
            log.info("===============> 客户端 [" + getIpByClient(client) + "] 已连接 <===============");
            String id = getParamsByKey(client, ParamsKeyEnum.ID);
            if (id != null) {
                clientMap.remove(id);
                client.disconnect();
            }
        });

        // 监听的'GET_TRANSPORT_ROUTE'事件
        socketIOServer.addEventListener(ServiceEvent.GET_TRANSPORT_ROUTE, String.class, (client, data, ackSender) -> {
            if (StrUtil.isBlank(data)) {
                client.sendEvent(ServiceEvent.GET_TRANSPORT_ROUTE, JSONUtil.toJsonStr(ResponseResult.error("运输信息ID不能为空！")));
                client.disconnect();
                return;
            }
            Long transportInfoId = Long.valueOf(data);
            TransportInfo transportInfo = transportInfoService.getById(transportInfoId);
            if (transportInfo == null) {
                client.sendEvent(ServiceEvent.GET_TRANSPORT_ROUTE, JSONUtil.toJsonStr(ResponseResult.error("该运输信息不存在，无法查看！")));
                client.disconnect();
                return;
            }
            if (transportInfo.getTransportState().equals(TransportState.NOT_START)) {
                client.sendEvent(ServiceEvent.GET_TRANSPORT_ROUTE, JSONUtil.toJsonStr(ResponseResult.error("该运输还为开始，无法查看！")));
                client.disconnect();
                return;
            }
            new Thread(() -> {
                Integer serialNumber = 1;
                while (client.isChannelOpen()) {
                    Map<String, List<List<Object>>> transportRoute = gpsInfoService.getTransportRouteOfWebSocket(transportInfoId, serialNumber);
                    if (transportRoute == null) {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            log.error("系统休眠出错！{}", e.getMessage(), e);
                        }
                        continue;
                    }
                    client.sendEvent(ServiceEvent.GET_TRANSPORT_ROUTE, JSONUtil.toJsonStr(ResponseResult.success(transportRoute)));
                    serialNumber = serialNumber + transportRoute.get("latlngs").size();
                }
            }).start();
        });

        // 启动服务
        socketIOServer.start();

    }

    @Override
    public void stop() {
        if (socketIOServer != null) {
            socketIOServer.stop();
            socketIOServer = null;
        }
    }

    @Override
    public void pushMessageToUser(String userId, String msgContent) {
        SocketIOClient client = clientMap.get(userId);
        if (client != null) {
            client.sendEvent(ServiceEvent.PUSH_DATA_EVENT, msgContent);
        }
    }

    private String getParamsByKey(SocketIOClient client, ParamsKeyEnum paramsKey) {
        Map<String, List<String>> params = client.getHandshakeData().getUrlParams();
        if (params.containsKey(paramsKey.getKey())) {
            List<String> list = params.get(paramsKey.getKey());
            if (!CollectionUtils.isEmpty(list)) {
                return list.get(0);
            }
        }
        return null;
    }

    /**
     * 获取连接的客户端ip地址
     *
     * @param client: 客户端
     * @return: java.lang.String
     */
    private String getIpByClient(SocketIOClient client) {
        String sa = client.getRemoteAddress().toString();
        return sa.substring(1, sa.indexOf(":"));
    }

    /**
     * 查看监控
     *
     * @param iotEquipmentId  物联网设备ID
     * @param transportInfoId 运输信息ID
     */
    @Override
    public void viewMonitor(Long iotEquipmentId, Long transportInfoId) {
        SocketIOClient socketIOClient = clientMap.get(iotEquipmentId);
        if (socketIOClient == null) {
            throw new BizException("该设备未连接！");
        }
        socketIOClient.sendEvent(ServiceEvent.PUSH_VIDEO_EVENT, transportInfoId);
    }

}
