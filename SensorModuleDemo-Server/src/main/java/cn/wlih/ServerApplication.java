package cn.wlih;

import cn.wlih.video.VideoDemo001;

/**
 * Created by IntelliJ IDEA.
 *
 * @author 王立宏
 * 描述:
 * path: SensorModuleDemo-cn.wlih-ServerApplication
 * date: 2023/8/25 9:37
 */
public class ServerApplication {
    public static void main(String[] args) {
        System.out.println("=========================> 服务端启动成功！ <=========================");
        System.out.println("--->" + VideoDemo001.PROJECT_PATH);
        System.out.println("--->" + VideoDemo001.VIDEO_PATH);
        System.out.println("--->" + System.getProperty("file.separator"));
        System.out.println();
    }
}
