package cn.wlih.util;

import lombok.Data;

/**
 * 描述：
 * <p>
 *     https://segmentfault.com/a/1190000015362485
 * </p>
 * @author 王立宏
 * @date 2023/10/16 16:09
 * @path SensorModuleDemo-cn.wlih.util-SSHConfig
 */
@Data
public class SSHConfig {

    private String ip = "raspberrypi.local";
    private String userName = "pi";
    private String password = "raspberry";
    private String key;
    private int port = 22;

    private int timeout = 3000;

}
