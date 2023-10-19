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

    private String ip = "wlih.cn";
    private String userName = "root";
    private String password;
    private String key = "/Users/Data/.ssh/a.pem";
    private int port = 22;

    private int timeout = 3000;

}
