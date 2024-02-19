package cn.wlih.appPublic.enums;

/**
 * 程序运行模式
 */
public enum ProgramInvocationPattern {

    EXIT(-1, "关闭程序"),
    STOP(0, "停止程序"),
    START(1, "启动程序"),
    LOOP(2, "循环调用");

    private Integer code;
    private String desc;

    ProgramInvocationPattern(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }
    public String getDesc() {
        return desc;
    }
}
