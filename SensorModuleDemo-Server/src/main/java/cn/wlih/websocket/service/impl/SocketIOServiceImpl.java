package cn.wlih.websocket.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.wlih.app.dao.TransportInfoMapper;
import cn.wlih.app.model.TransportInfo;
import cn.wlih.app.model.modelDbEnum.TransportState;
import cn.wlih.app.service.TransportInfoService;
import cn.wlih.core.base.model.ResponseResult;
import cn.wlih.core.myError.BizException;
import cn.wlih.sensormodule.service.GpsInfoService;
import cn.wlih.websocket.service.ISocketIOService;
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
public class SocketIOServiceImpl implements ISocketIOService {

    /**
     * 存放已连接的客户端
     */
    private static Map<String, SocketIOClient> clientMap = new ConcurrentHashMap<>();

    /**
     * 自定义事件`push_data_event`,用于服务端与客户端通信
     */
    private static final String PUSH_DATA_EVENT = "push_data_event";
    /**
     * 获取运输路线 getTransportRoute
     */
    private static final String GET_TRANSPORT_ROUTE = "GET_TRANSPORT_ROUTE";

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
            log.debug("************ 客户端： " + getIpByClient(client) + " 已连接 ************");
            // 自定义事件`connected` -> 与客户端通信  （也可以使用内置事件，如：Socket.EVENT_CONNECT）
            client.sendEvent("connected", "你成功连接上了哦...");
            String userId = getParamsByClient(client);
            if (userId != null) {
                clientMap.put(userId, client);
            }
        });

        // 监听客户端断开连接
        socketIOServer.addDisconnectListener(client -> {
            String clientIp = getIpByClient(client);
            log.debug(clientIp + " *********************** " + "客户端已断开连接");
            String userId = getParamsByClient(client);
            if (userId != null) {
                clientMap.remove(userId);
                client.disconnect();
            }
        });

        // 自定义事件`client_info_event` -> 监听客户端消息
        socketIOServer.addEventListener(PUSH_DATA_EVENT, String.class, (client, data, ackSender) -> {
            // 客户端推送`client_info_event`事件时，onData接受数据，这里是string类型的json数据，还可以为Byte[],object其他类型
            String clientIp = getIpByClient(client);
            String paramsByClient = getParamsByClient(client);
            log.debug(paramsByClient + " ************ 客户端：" + data);
        });

        // 监听的'GET_TRANSPORT_ROUTE'事件
        socketIOServer.addEventListener(GET_TRANSPORT_ROUTE, String.class, (client, data, ackSender) -> {
            if (StrUtil.isBlank(data)) {
                client.sendEvent(GET_TRANSPORT_ROUTE, JSONUtil.toJsonStr(ResponseResult.error("运输信息ID不能为空！")));
                client.disconnect();
                return;
            }
            Long transportInfoId = Long.valueOf(data);
            TransportInfo transportInfo = transportInfoService.getById(transportInfoId);
            if (transportInfo == null) {
                client.sendEvent(GET_TRANSPORT_ROUTE, JSONUtil.toJsonStr(ResponseResult.error("该运输信息不存在，无法查看！")));
                client.disconnect();
                return;
            }
            if (transportInfo.getTransportState().equals(TransportState.NOT_START)) {
                client.sendEvent(GET_TRANSPORT_ROUTE, JSONUtil.toJsonStr(ResponseResult.error("该运输还为开始，无法查看！")));
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
                            log.warn(e.getMessage());
                        }
                        continue;
                    }
                    client.sendEvent(GET_TRANSPORT_ROUTE, JSONUtil.toJsonStr(ResponseResult.success(transportRoute)));
                    serialNumber = serialNumber + transportRoute.get("latlngs").size();
                }
            }).start();
        });

        // 启动服务
        socketIOServer.start();

        // broadcast: 默认是向所有的socket连接进行广播，但是不包括发送者自身，如果自己也打算接收消息的话，需要给自己单独发送。
        new Thread(() -> {
            int i = 0;
            while (true) {
                try {
                    // 每3秒发送一次广播消息
                    Thread.sleep(3000);
                    socketIOServer.getBroadcastOperations().sendEvent("myBroadcast", "广播消息 " + DateUtil.now());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
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
            client.sendEvent(PUSH_DATA_EVENT, msgContent);
        }
    }

    /**
     * 获取客户端url中的userId参数（这里根据个人需求和客户端对应修改即可）
     *
     * @param client: 客户端
     * @return: java.lang.String
     */
    private String getParamsByClient(SocketIOClient client) {
        // 获取客户端url参数（这里的userId是唯一标识）
        Map<String, List<String>> params = client.getHandshakeData().getUrlParams();
        List<String> userIdList = params.get("RequestType");
        if (!CollectionUtils.isEmpty(userIdList)) {
            return userIdList.get(0);
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
        String clientIp = sa.substring(1, sa.indexOf(":"));
        return clientIp;
    }

}
