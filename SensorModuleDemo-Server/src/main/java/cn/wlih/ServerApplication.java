package cn.wlih;

import cn.wlih.demo.video.VideoDemo001;
import cn.wlih.util.FileUtil;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by IntelliJ IDEA.
 *
 * @author 王立宏
 * 描述:
 * path: SensorModuleDemo-cn.wlih-ServerApplication
 * date: 2023/8/25 9:37
 */
public class ServerApplication {

    private static final Integer PORT = 8080;
    /**
     * 建一个线程池
     */
    private ExecutorService pool = Executors.newFixedThreadPool(20);

    public static void main(String[] args) throws IOException {
        new ServerApplication().build();
    }


    private void build() {
        try {
            //端口
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("=========================> 服务端启动成功！ <=========================");
            //循环去接收响应
            while (true) {
                //接收连接进来的客户端
                Socket socket = serverSocket.accept();//接收不到就一直等着
                //System.out.println("accept socket:"+accept);//接收到请求
                //开启一个子线程
                pool.execute(()->{
                    try {
                        File file = new File(FileUtil.TEST_VIDEO_FILE);
//                        if (!file.exists()) file.mkdir();
                        InputStream cameraStream = socket.getInputStream();
                        FileOutputStream outputStream = new FileOutputStream(file);
                        int content = cameraStream.read();
                        while (content != -1) {
                            outputStream.write(content);
                            content = cameraStream.read();
                            outputStream.flush();
                        }
                        cameraStream.close();
                        outputStream.close();
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
