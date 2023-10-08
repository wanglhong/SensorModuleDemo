package cn.wlih.demo.video;

import cn.wlih.sys.util.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 描述:
 * @author 王立宏
 * @date 2023/9/20 10:45
 * @path SensorModuleDemo-cn.wlih.demo.video-VideoDemo002
 */
public class VideoDemo002 {

    /**
     * 程序使用的端口
     */
    private static final Integer PORT = 8080;
    /**
     * 建一个线程池
     */
    private ExecutorService pool = Executors.newFixedThreadPool(20);

    /**
     * socket接收树莓派传入的视频
     */
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
