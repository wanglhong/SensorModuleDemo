package cn.wlih.component.apps;

import cn.wlih.component.ComponentApp;
import cn.wlih.component.ComponentAppEnum;
import cn.wlih.utils.factoryUtils.MyPostConstruct;
import com.pi4j.context.Context;

public class GpsComponentApp extends BaseApp implements ComponentApp {

    @MyPostConstruct
    public void register() {
        this.componentAppEnum = ComponentAppEnum.GPS;
    }

    /**
     * 初始化配置组件信息
     *
     * @param pi4jContext Pi4j上下文
     */
    @Override
    public void init(Context pi4jContext) {
        this.pi4jContext = pi4jContext;
        this.componentAppEnum = ComponentAppEnum.GPS;
    }

}
