package cn.wlih.component;

public enum ComponentAppEnum {

    GPS("GPS", "GPS组件");

    /**
     * 组件名称
     */
    private String componentName;
    /**
     * 组件描述
     */
    private String componentDesc;

    ComponentAppEnum(String componentName, String componentDesc) {
        this.componentName = componentName;
        this.componentDesc = componentDesc;
    }

    /**
     * 获取组件名称
     * @return
     */
    public String getComponentName() {
        return componentName.toUpperCase();
    }

    /**
     * 获取组件描述
     * @return
     */
    public String getComponentDesc() {
        return componentDesc;
    }
}
