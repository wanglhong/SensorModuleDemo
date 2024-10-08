package cn.wlih;

import cn.wlih.component.ComponentFactory;
import cn.wlih.appPublic.enums.ProgramInvocationPattern;
import cn.wlih.utils.LoggerUtil;
import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.library.pigpio.PiGpio;
import com.pi4j.plugin.linuxfs.provider.i2c.LinuxFsI2CProvider;
import com.pi4j.plugin.pigpio.provider.gpio.digital.PiGpioDigitalInputProvider;
import com.pi4j.plugin.pigpio.provider.gpio.digital.PiGpioDigitalOutputProvider;
import com.pi4j.plugin.pigpio.provider.pwm.PiGpioPwmProvider;
import com.pi4j.plugin.pigpio.provider.serial.PiGpioSerialProvider;
import com.pi4j.plugin.pigpio.provider.spi.PiGpioSpiProvider;
import com.pi4j.plugin.raspberrypi.platform.RaspberryPiPlatform;
import io.socket.client.IO;
import io.socket.client.Socket;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * 描述: 客户端启动类
 * @author 王立宏
 * @date 2023/8/25 9:36
 * @path SensorModuleDemo-cn.wlih-ClientApplication
 */
@Slf4j
public class ClientApplication {

    private static final ComponentFactory componentFactory = new ComponentFactory();
    private Context pi4jContext;

    /**
     * 程序入口
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        String argsStr = Arrays.toString(args);
        String appId = "1774067734078820351";
        LoggerUtil.logTitle(log, 100, "SensorModuleDemo-Client",
                "ClientID -- " + appId,
                "args --> " + argsStr);
        String ip = "wlih.cn";
        String port = "8081";
        if (args.length > 0 && args.length <= 2) {
            if (args.length == 2) {
                ip = args[0];
                port = args[1];
            } else {
                ip = args[0];
            }
        }
        String url = "http://" + ip + ":" + port;
        try {
            IO.Options options = new IO.Options();
            options.transports = new String[]{"websocket"};
            options.reconnectionAttempts = 2;
            // 失败重连的时间间隔
            options.reconnectionDelay = 1000;
            // 连接超时时间(ms)
            options.timeout = 500;
            // userId: 唯一标识 传给服务端存储
            final Socket socket = IO.socket(url + "?id=" + appId, options);

            socket.on(Socket.EVENT_CONNECT, args1 -> socket.send("hello..."));

            // 接收服务端成功连接消息
            socket.on(ClientEvent.CONNECTED_EVENT, objects -> log.info("connected info --> " + objects[0].toString()));

            // 接收服务端消息
            socket.on(ClientEvent.PUSH_DATA_EVENT, objects -> log.debug("服务端:" + objects[0].toString()));

            // 接收服务端广播消息
            socket.on(ClientEvent.MY_BROADCAST_EVENT, objects -> log.debug("服务端：" + objects[0].toString()));

            // 推送视频消息
            socket.on(ClientEvent.PUSH_VIDEO_EVENT, objects -> {
                // raspivid -w 640 -h 360 -b 15000000 -t 0 -a 12 -a 1024 -a "CAM-1 %Y-%m-%d %X" -ae 18,0xff,0x808000 -o - | ffmpeg -re -i - -s 640x360 -vcodec copy -acodec copy -b:v 800k -b:a 32k -f flv rtmp://wlih.cn:1935/stream/1003
                // 执行命令
                try {
                    // 创建一个ProcessBuilder实例
                    ProcessBuilder processBuilder = new ProcessBuilder();
                    // 要执行的命令
                    StringBuilder command = new StringBuilder("raspivid -w 640 -h 360 -b 15000000 -t 0 -a 12 -a 1024 -a \"CAM-1 %Y-%m-%d %X\" -ae 18,0xff,0x808000 -o - | ffmpeg -re -i - -s 640x360 -vcodec copy -acodec copy -b:v 800k -b:a 32k -f flv ");
                    command.append("rtmp://").append("wlih.cn").append(":").append("1935").append("/stream/").append(objects[0].toString());
                    log.info("command --> " + command);
                    processBuilder.command("bash", "-c", command.toString());  // 对于Windows，可以使用 processBuilder.command("cmd.exe", "/c", "dir");
                    // 启动进程
                    Process process = processBuilder.start();
                    // 获取命令执行的输出
                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        log.info(line);
                    }
                    // 等待命令执行完成
                    int exitCode = process.waitFor();
                    log.info("Exit code: {}", exitCode);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            });
            // 连接
            socket.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



















    public ClientApplication() {
        // 初始化 Pi4j 上下文
        final PiGpio piGpio = PiGpio.newNativeInstance();
        pi4jContext = Pi4J.newContextBuilder()
                .noAutoDetect()
                .add(new RaspberryPiPlatform() {
                    @Override
                    protected String[] getProviders() {
                        return new String[]{};
                    }
                })
                .add(PiGpioDigitalInputProvider.newInstance(piGpio),
                        PiGpioDigitalOutputProvider.newInstance(piGpio) ,
                        PiGpioPwmProvider.newInstance(piGpio),
                        PiGpioSerialProvider.newInstance(piGpio),
                        PiGpioSpiProvider.newInstance(piGpio),
                        LinuxFsI2CProvider.newInstance()
                )
                .build();
        all : while (true) {
            // TODO　查询 redis 中的登录信息 判断是否需要继续运行
            switch (ProgramInvocationPattern.valueOf("CLIENT")) {
                case EXIT -> {
                    log.warn("程序退出，并关闭计算机！");
                    break all;
                } case STOP -> {
                    log.info("程序停止，不进行信息收集。");
                } case START -> {
                    log.info("程序启动");
                } case LOOP -> {
                    log.info("程序循环");
                }
            }
            // 查询程序调用模式
        }
        // 程序退出时释放 pi4j 上下文资源
        pi4jContext.shutdown();
        // 关闭计算机
    }

}
