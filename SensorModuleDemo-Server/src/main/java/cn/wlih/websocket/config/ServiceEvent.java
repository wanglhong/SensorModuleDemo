package cn.wlih.websocket.config;

/**
 * 服务端自定义事件
 */
public class ServiceEvent {

    /**
     * 连接成功事件后服务端发送的消息
     */
    public static final String CONNECTED_EVENT = "CONNECTED_EVENT";

    /**
     * 推送消息至客户端
     */
    public static final String PUSH_DATA_EVENT = "PUSH_DATA_EVENT";

    /**
     * 获取运输路线 getTransportRoute
     */
    public static final String GET_TRANSPORT_ROUTE = "GET_TRANSPORT_ROUTE";

    /**
     * 推送视频消息
     */
    public static final String PUSH_VIDEO_EVENT = "PUSH_VIDEO_EVENT";

}
