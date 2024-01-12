package cn.wlih.component;

import com.pi4j.context.Context;

/**
 * 组件启动类
 */
public interface ComponentApp {

    /**
     * 初始化配置组件信息
     * @param pi4jContext Pi4j上下文
     */
    void init(Context pi4jContext);

}
