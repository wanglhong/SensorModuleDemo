package cn.wlih;

/**
 * 客户端自定义事件
 */
public class ClientEvent {

    /**
     * 连接成功事件后服务端发送的消息
     */
    public static final String CONNECTED_EVENT = "CONNECTED_EVENT";

    /**
     * 推送消息至客户端
     */
    public static final String PUSH_DATA_EVENT = "PUSH_DATA_EVENT";

    /**
     * 接收服务端广播消息 myBroadcast
     */
    public static final String MY_BROADCAST_EVENT = "MY_BROADCAST_EVENT";

    public static final String PUSH_VIDEO_EVENT = "PUSH_VIDEO_EVENT";
}
