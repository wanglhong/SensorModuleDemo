package cn.wlih.util;

/**
 * Created by IntelliJ IDEA.
 *
 * @author 王立宏
 * 描述:
 * path: SensorModuleDemo-cn.wlih.util-FileUtil
 * date: 2023/8/29 21:25
 */
public class FileUtil {


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
    public static final String TEST_VIDEO_FILE = VIDEO_PATH + PATH_SEPARATOR + "test.h264";

}
