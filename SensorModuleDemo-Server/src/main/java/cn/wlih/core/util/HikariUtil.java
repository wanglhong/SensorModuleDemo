package cn.wlih.core.util;

public class HikariUtil {

//    /**
//     * 监控 hikari 可用连接数代码，参考源码实现
//     * @see HikariPool#logPoolState(java.lang.String...)
//     */
//    public static String poolState(HikariDataSource dataSource, boolean ifLog, String... prefix) {
//        String poolName = dataSource.getPoolName();
//        HikariPoolMXBean mx = dataSource.getHikariPoolMXBean();
//        String format = String.format("%s - %sstats (total=%d, active=%d, idle=%d, waiting=%d)",
//                poolName, (prefix.length > 0 ? prefix[0] : ""),
//                mx.getTotalConnections(), mx.getActiveConnections(), mx.getIdleConnections(), mx.getThreadsAwaitingConnection());
//        if (ifLog) {
//            log.info("{}", format);
//        }
//        return format;
//    }

}
