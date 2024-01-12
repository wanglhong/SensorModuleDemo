package cn.wlih.component;

import cn.hutool.core.util.StrUtil;
import com.pi4j.context.Context;

import java.util.EnumMap;
import java.util.Map;

/**
 * 组件工厂
 */
public class ComponentFactory {

    private final Map<ComponentAppEnum, ComponentApp> componentMap = new EnumMap<>(ComponentAppEnum.class);

    /**
     * 注册组件到工厂
     * @param componentAppEnum 组件类型
     * @param ComponentApp 组件对象
     */
    public void registerComponentApp(ComponentAppEnum componentAppEnum, ComponentApp ComponentApp) {
        if (componentAppEnum == null || ComponentApp == null) {
            throw new IllegalArgumentException("参数不能为 NULL。");
        }
        if (componentMap.containsKey(componentAppEnum)) {
            throw new UnsupportedOperationException(
                    "组件 [" + componentAppEnum.name() + "] 已经注册。");
        }
        componentMap.put(componentAppEnum, ComponentApp);
    }

    /**
     * 根据组件类型获取组件
     */
    public ComponentApp getComponentApp(String componentName, Context pi4jContext) {
        if (StrUtil.isBlank(componentName)) {
            throw new RuntimeException("组件名称不能为空！");
        }
        ComponentAppEnum componentAppEnum = null;
        for (ComponentAppEnum componentAppEnum1 : ComponentAppEnum.values()) {
            if (componentAppEnum1.getComponentName().equals(componentName.toUpperCase())) {
                componentAppEnum = componentAppEnum1;
                break;
            }
        }
        if (componentAppEnum == null) {
            throw new RuntimeException("暂不支持 [" + componentName + "] 组件。");
        }
        ComponentApp componentApp = componentMap.get(componentAppEnum);
        if (componentApp == null) {
            throw new RuntimeException("组件 [" + componentAppEnum.name() + "] 不存在，请先添加 Jar 依赖。");
        }
        // 设置 Pi4j 上下文
        componentApp.init(pi4jContext);
        return componentApp;
    }

}
