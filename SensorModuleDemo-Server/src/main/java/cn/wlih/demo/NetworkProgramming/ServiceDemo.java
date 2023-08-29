package cn.wlih.demo.NetworkProgramming;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by IntelliJ IDEA.
 *
 * @author 王立宏
 * 描述: 网络编程Demo-服务端，配合SensorModuleDemo-Client客户端一起使用。
 * path: SensorModuleDemo-cn.wlih.demo.service-ServiceDemo
 * date: 2023/8/25 20:25
 */
public class ServiceDemo {

    private static final Integer PORT = 8080;

    //建一个线程池
    private ExecutorService pool = Executors.newFixedThreadPool(20);

    public static void main(String[] args) {
        new ServiceDemo().build();
    }

    private void build() {
        try {
            //端口
            ServerSocket ss = new ServerSocket(PORT);
            System.out.println("====================> 服务器已上线.... <====================");
            //循环去接收响应
            while (true) {
                //接收连接进来的客户端
                Socket socket = ss.accept();//接收不到就一直等着
                //System.out.println("accept socket:"+accept);//接收到请求
                //开启一个子线程
                pool.execute(()->{
                    try {
                        //接收客户端发来的消息
                        InputStream inputStream = socket.getInputStream();
                        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        String message = bufferedReader.readLine();
                        System.out.println(message);

                        //发送消息给客户端
                        OutputStream outputStream = socket.getOutputStream();
                        PrintStream printStream = new PrintStream(outputStream);
                        printStream.println("服务端：好的，接收到了你发来的消息！");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } finally {
                        //关闭Socket连接
                        try {
                            socket.close();
                            //System.out.println("close socket:"+accept);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
