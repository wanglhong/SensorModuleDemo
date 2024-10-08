package cn.wlih.websocket.service;

public interface SocketIOService {

    /**
     * 启动服务
     */
    void start();

    /**
     * 停止服务
     */
    void stop();

    /**
     * 推送信息给指定客户端
     *
     * @param userId:     客户端唯一标识
     * @param msgContent: 消息内容
     */
    void pushMessageToUser(String userId, String msgContent);

    /**
     * 查看监控
     * @param iotEquipmentId 物联网设备ID
     * @param transportInfoId 运输信息ID
     */
    void viewMonitor(Long iotEquipmentId, Long transportInfoId);

}
