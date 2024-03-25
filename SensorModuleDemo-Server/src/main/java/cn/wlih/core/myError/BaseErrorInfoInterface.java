package cn.wlih.core.myError;

/**
 * 服务接口类
 */
public interface BaseErrorInfoInterface {

    /**
     * @return 错误码
     */
    Integer getResultCode();

    /**
     * @return 错误描述
     */
    String getResultMsg();

}
