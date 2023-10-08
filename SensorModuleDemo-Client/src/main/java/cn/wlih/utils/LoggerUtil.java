package cn.wlih.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述:
 * @author 王立宏
 * @date 2023/9/19 10:03
 * @path SensorModuleDemo-cn.wlih.utils-LoggerUtil
 */
public class LoggerUtil {

    public static final Logger loggerRed = LoggerFactory.getLogger("Red");
    public static final Logger loggerBlue = LoggerFactory.getLogger("Blue");
    public static final Logger loggerGreen = LoggerFactory.getLogger("Green");
    public static final Logger loggerYellow = LoggerFactory.getLogger("Yellow");

    public static final Logger loggerMagenta = LoggerFactory.getLogger("Magenta");
    public static final Logger loggerCyan = LoggerFactory.getLogger("Cyan");
    public static final Logger main = LoggerFactory.getLogger("White");

    private static final Map<String, Logger> map = new HashMap<>();

    static {
        map.put("1", loggerRed);
        map.put("2", loggerBlue);
        map.put("3", loggerGreen);
        map.put("4", loggerYellow);
        map.put("5", loggerMagenta);
        map.put("6", loggerCyan);
        map.put("0", loggerCyan);
        map.put("main", main);
    }

    public static Logger get() {
        return get(null);
    }

    public static Logger get(String prefix) {
        String name = Thread.currentThread().getName();
        if(!name.equals("main")) {
            int length = name.length();
            name = name.substring(length - 1);
        }
        return map.getOrDefault(name, loggerRed);
    }

    public static void main(String[] args) {
        loggerRed.debug("loggerRed");
        loggerBlue.debug("loggerBlue");
        loggerGreen.debug("loggerGreen");
        loggerYellow.debug("loggerYellow");

        loggerMagenta.warn("loggerMagenta-warn");
        loggerCyan.info("loggerCyan-info");
        main.error("main-error");
    }

}
