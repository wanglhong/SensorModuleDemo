package cn.wlih.utils;

import org.slf4j.Logger;
import cn.hutool.core.util.StrUtil;

/**
 * 描述:
 * @author 王立宏
 * @date 2023/9/19 10:03
 * @path SensorModuleDemo-cn.wlih.utils-LoggerUtil
 */
public class LoggerUtil {

    public static void logTitle(Logger logger, Integer length, String titleMsg, String... msg) {
        if (null == length || length < 1) length = 100;
        logger.info(StrUtil.repeat("*", length));
        logger.info(StrUtil.repeat("*", length));
        logger.info(StrUtil.fixLength("* *", ' ', length - 3) + "* *");
        logger.info("* *" + StrUtil.center("<-- " + titleMsg + " -->", length - 6) + "* *");
        for (String s : msg) {
            logger.info("* *" + StrUtil.center(s, length - 6) + "* *");
        }
        logger.info(StrUtil.fixLength("* *", ' ', length - 3) + "* *");
        logger.info(StrUtil.repeat("*", length));
        logger.info(StrUtil.repeat("*", length));
    }

    public static void logBox(Logger logger, Integer length, String... msgs) {
        if (null == length || length < 1) length = 60;
        logger.info(StrUtil.repeat("-", length));
        logger.info(StrUtil.fixLength("|", ' ', length - 1) + "|");
        for (String msg : msgs) {
            msg = "| " + msg;
            logger.info(StrUtil.fixLength(msg, ' ', length - 1) + "|");
        }
        logger.info(StrUtil.fixLength("|", ' ', length - 1) + "|");
        logger.info(StrUtil.repeat("-", length));
    }

}
