package cn.wlih.utils;

import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 描述:
 * @author 王立宏
 * @date 2023/9/20 22:52
 * @path SensorModuleDemo-cn.wlih.utils-IPUtil
 */
@Slf4j
public class IPUtil {

    public static final String SERVICE_NAME = "LAPTOP-EU423IEB.local";
    public static final Integer PORT = 8082;
    public static String SERVER_IP;

    static {
        SERVER_IP = getHostAddressByName(SERVICE_NAME);
    }

    private static String getHostAddressByName(String name) {
        String hostAddress = SERVICE_NAME;
        try {
            InetAddress[] addresses = InetAddress.getAllByName(name);
            for (int i = 0; i < addresses.length; i++) {
                InetAddress address = addresses[i];
                hostAddress = address.getHostAddress();
                log.info("[ping]-[" + name + "]-[" + i + "] --> " + hostAddress);
            }
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        return hostAddress;
    }

}
