package cn.wlih.demo.video;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 描述: 接收树莓怕视频Demo001（https://blog.csdn.net/weixin_42534940/article/details/89426240）
 * 树莓派操作：raspivid -w 1920 -h 1080 -t 5000 -o tcp://IP:Port
 *      录制 格式640*480， 时长 5 S的视频并已TCP协议发送到192.168.1.101的3333端口（长时间录制：-t 的参数5000设置为0）
 * 服务端接收处理视频（本类中）
 * @author 王立宏
 * @date 2023/8/25 16:35
 * @path SensorModuleDemo-cn.wlih.video-VideoDemo001
 */
public class VideoDemo001 {

    /**
     * 项目路径
     */
    public static final String PROJECT_PATH = System.getProperty("user.dir");
    /**
     * 路径分隔符
     */
    public static final String PATH_SEPARATOR = System.getProperty("file.separator");
    /**
     * 视频存储路径
     */
    public static final String VIDEO_PATH = PROJECT_PATH + PATH_SEPARATOR + "video";

    /**
     * 视频测试存储文件
     */
    private static final String TEST_VIDEO_FILE = VIDEO_PATH + PATH_SEPARATOR + "test.h264";

    /**
     * 接收树莓派发送过来的视频数据
     * @throws IOException IOException
     */
    public void saveVideoStream() throws IOException {
        ServerSocket cameraSS = new ServerSocket(3333);
        Socket cameraSocket;
        while (true) {
            cameraSocket = cameraSS.accept();
            File file = new File(TEST_VIDEO_FILE);
            InputStream cameraStream = cameraSocket.getInputStream();
            FileOutputStream outputStream = new FileOutputStream(file);
            int content = cameraStream.read();
            while (content != -1) {
                outputStream.write(content);
                content = cameraStream.read();
                outputStream.flush();
            }
            cameraStream.close();
            outputStream.close();
        }
    }

}
