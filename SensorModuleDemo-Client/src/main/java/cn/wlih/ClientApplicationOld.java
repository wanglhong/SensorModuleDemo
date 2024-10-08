package cn.wlih;

import cn.wlih.demo.Pi4j.gpsDemo.SerialGps_App;
import cn.wlih.utils.IPUtil;
import cn.wlih.utils.LoggerUtil;
import com.pi4j.Pi4J;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;

@Slf4j
public class ClientApplicationOld {


    public static void main(String[] args) throws InterruptedException, IOException {
        for (int i = 0; i < 10; i++) {
            LoggerUtil.logTitle(log, null, "Maven Refactoring Tests");
            Thread.sleep(1000);
        }
//        new ClientApplication().sensorModule();
//        new ClientApplication().logTest();
//        ProcessBuilder pb = new ProcessBuilder("raspivid", "-o", "output.h264");
//        Process process = pb.start();
//
//        // 等待该过程完成
//        process.waitFor(5, TimeUnit.MINUTES);
//
//        // 检查进程是否超时
//        if (process.isAlive()) {
//            process.destroy();
//            throw new RuntimeException("录制超时");
//        }
    }

    /**
     * 树莓派引脚测试
     */
    public void sensorModule() throws InterruptedException {
//        Pi4jDemoOfHA pi4jDemoOfHA = new Pi4jDemoOfHA();
//        pi4jDemoOfHA.demo01();
        new SerialGps_App().execute(Pi4J.newAutoContext());
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
