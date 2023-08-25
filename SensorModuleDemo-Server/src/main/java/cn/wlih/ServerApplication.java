package cn.wlih;

import cn.wlih.demo.video.VideoDemo001;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 *
 * @author 王立宏
 * 描述:
 * path: SensorModuleDemo-cn.wlih-ServerApplication
 * date: 2023/8/25 9:37
 */
public class ServerApplication {
    public static void main(String[] args) throws IOException {
        System.out.println("=========================> 服务端启动成功！ <=========================");
        VideoDemo001 videoDemo001 = new VideoDemo001();
        videoDemo001.saveVideoStream();
        System.out.println("--->" + VideoDemo001.PROJECT_PATH);
        System.out.println("--->" + VideoDemo001.VIDEO_PATH);
        System.out.println("--->" + System.getProperty("file.separator"));
        System.out.println();
    }
}
