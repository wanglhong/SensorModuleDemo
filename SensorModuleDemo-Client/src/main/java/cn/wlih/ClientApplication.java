package cn.wlih;

import cn.wlih.demo.Pi4j.Pi4jDemoOfHA;
import cn.wlih.demo.Pi4j.PyroelectricSensorDemo;

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
public class ClientApplication {

    private static final String IP = "192.168.251.231";
    private static final Integer PORT = 8080;

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("=========================> 客户端启动成功！ <=========================\n");
        PyroelectricSensorDemo pyroelectricSensorDemo = new PyroelectricSensorDemo();
        pyroelectricSensorDemo.psDemo();
//        pyroelectricSensorDemo.demo02();
        System.out.println("\n=========================> 客户端退出成功！ <=========================");
    }

    public void buile() throws IOException {
        Socket socket = new Socket(IP, PORT);
        //发送消息给服务端
        OutputStream outputStream = socket.getOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        printStream.println("客户端：客户端发送给服务端的消息！");

        //接收服务端发来的消息
        InputStream inputStream = socket.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String message = bufferedReader.readLine();
        System.out.println(message);
    }

}
