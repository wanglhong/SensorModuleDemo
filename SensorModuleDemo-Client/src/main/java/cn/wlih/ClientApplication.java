package cn.wlih;

import cn.wlih.demo.Pi4j.Pi4jDemoOfHA;
import cn.wlih.utils.IPUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;

/**
 * Created by IntelliJ IDEA.
 *
 * @author 王立宏
 * 描述: 客户端启动类
 * path: SensorModuleDemo-cn.wlih-ClientApplication
 * date: 2023/8/25 9:36
 */
@Slf4j
public class ClientApplication {

    public static void main(String[] args) throws InterruptedException {
        new ClientApplication().sensorModule();
    }

    /**
     * 树莓派引脚测试
     */
    public void sensorModule() throws InterruptedException {
        Pi4jDemoOfHA pi4jDemoOfHA = new Pi4jDemoOfHA();
        pi4jDemoOfHA.demo01();
    }

    /**
     * 日志测试
     */
    public void logTest() {
        log.info("========================================> 日志打印测试启动成功！ <========================================");
        log.trace("This is a Main trace message.");
        log.debug("This is a Main debug message.");
        log.info("This is a Main info message.");
        log.warn("This is a Main warn message.");
        log.error("This is a Main error message.");
        log.info("========================================> 日志打印测试退出成功！ <========================================");
    }

    /**
     * 网络编程测试
     */
    public void buile() {
        try {
            Socket socket = new Socket(IPUtil.SERVER_IP, IPUtil.PORT);
            //发送消息给服务端
            OutputStream outputStream = socket.getOutputStream();
            PrintStream printStream = new PrintStream(outputStream);
            printStream.println("客户端：客户端发送给服务端的消息！");

            //接收服务端发来的消息
            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String message = bufferedReader.readLine();
            log.info(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
