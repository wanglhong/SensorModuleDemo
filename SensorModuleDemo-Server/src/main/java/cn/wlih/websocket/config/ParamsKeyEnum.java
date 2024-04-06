package cn.wlih.websocket.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * WebSocket参数枚举
 */
@Getter
@AllArgsConstructor
public enum ParamsKeyEnum {
    ID("id", "客户端ID");

    private final String key;
    private final String desc;

}
