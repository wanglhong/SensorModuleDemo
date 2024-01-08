package cn.wlih.demo.Pi4j.gpsDemo;

import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

@Slf4j
public abstract class Component {
    /**
     * 记录器实例
     */
    private static final Logger logger = Logger.getLogger("Pi4J Components");
//    private static final Logger logger = log;

    static {
        Level appropriateLevel = Level.INFO;
        //Level appropriateLevel = Level.FINE; //如果为“debug”，则使用

        System.setProperty("java.util.logging.SimpleFormatter.format",
                "%4$s: %5$s [%1$tl:%1$tM:%1$tS %1$Tp]%n");

        logger.setLevel(appropriateLevel);
        logger.setUseParentHandlers(false);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(appropriateLevel);
        logger.addHandler(handler);
    }

    protected Component(){
    }

    /**
     * 重写此方法以清理所有已用的资源
     */
    public void reset(){
        //默认情况下无事可做
    }

    protected void logInfo(String msg, Object... args) {
        logger.info(() -> String.format(msg, args));
    }

    protected void logError(String msg, Object... args) {
        logger.severe(() -> String.format(msg, args));
    }

    protected void logDebug(String msg, Object... args) {
        logger.fine(() -> String.format(msg, args));
    }

    protected void logException(String msg, Throwable exception){
        logger.log(Level.SEVERE, msg, exception);
    }

    /**
     * 休眠指定毫秒数的实用函数。
     * An {@link InterruptedException} 将在再次设置中断标志时被捕获并忽略。
     *
     * @param duration 睡眠时间
     */
    protected void delay(Duration duration) {
        try {
            long nanos = duration.toNanos();
            long millis = nanos / 1_000_000;
            int remainingNanos = (int) (nanos % 1_000_000);
            Thread.sleep(millis, remainingNanos);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    protected <T> T asMock(Class<T> type, Object instance) {
        return type.cast(instance);
    }
}
