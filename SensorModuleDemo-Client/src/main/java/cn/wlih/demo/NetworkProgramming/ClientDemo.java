package cn.wlih.demo.NetworkProgramming;

import java.io.*;
import java.net.Socket;

/**
 * Created by IntelliJ IDEA.
 *
 * @author 王立宏
 * 描述:
 * path: SensorModuleDemo-cn.wlih.demo.NetworkProgramming-ClientDemo
 * date: 2023/8/29 20:56
 */
public class ClientDemo {

    private static final String IP = "192.168.251.231";
    private static final Integer PORT = 8080;

    public static void main(String[] args) throws IOException {
        Socket socket = getSocket();
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

    /**
     * 获取socket连接
     * @return Socket
     */
    public static Socket getSocket() {
        try {
            Socket socket = new Socket(IP, PORT);
            return socket;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
