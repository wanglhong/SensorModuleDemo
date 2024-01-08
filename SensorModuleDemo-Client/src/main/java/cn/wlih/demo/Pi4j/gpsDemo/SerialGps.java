package cn.wlih.demo.Pi4j.gpsDemo;

import com.pi4j.context.Context;

import java.util.Locale;
import java.util.function.Consumer;

/**
 * 该组件通过串行 UART 总线将 GPS 信息作为 NMEA 语句发送。
 * <p>
 * see <a href="http://aprs.gids.nl/nmea/">GPS - NMEA sentence information</a>
 * <p>
 * GPS模块没有什么特别之处。它只是通过串口发送一些数据。
 * 我们可以使用标准的“SerialDevice”并对其进行适当的配置。
 *
 * SerialGps 只是将 SerialReader 传递的字符串转换为位置，包括经度、纬度、海拔高度
 *
 */
public class SerialGps extends Component {
    //只有当传感器发生明显移动时，才会报告新位置
    private static final double MIN_DISTANCE_M = 1.0;

    private final SerialDevice device;

    private final Consumer<GeoPosition> onNewPosition;
    private final Consumer<Double>      onNewAltitude;

    private int numberOfSatellites = 0;

    private GeoPosition lastReportedPosition = new GeoPosition(0,0);
    private double      lastReportedAltitude = -999;


    /**
     *
     * @param pi4j the good old Pi4J context
     * @param onNewPosition 如果设备发生了显着移动，则将调用
     * @param onNewAltitude 如果设备有新的高度，将调用
     */
    public SerialGps(Context pi4j,
                     Consumer<GeoPosition> onNewPosition,
                     Consumer<Double>      onNewAltitude
                     ) {
        this.onNewPosition = onNewPosition;
        this.onNewAltitude = onNewAltitude;
        device = new SerialDevice(pi4j, this::handleNewData);
    }

    public void start() {
        device.startReading();
        logInfo("开始读取 GPS 数据");
    }

    public void stop() {
        device.stopReading();
        logInfo("停止读取 GPS 数据");
    }

    @Override
    public void reset() {
        device.reset();
        super.reset();
        logInfo("Stopped reading GPS data");
    }

    /**
     * 当 SerialReader 提供新线时，SerialGPS 必须将其转换为新的地理位置和高度。
     * <p>
     * 有关交付的字符串，请参阅 <a href="http://aprs.gids.nl/nmea/">GPS - NMEA 句子信息</a>
     * </p>
     *
     * @param line SerialReader 传递的字符串
     */
    private void handleNewData(String line) {
        logDebug("Serial reader delivered: '%s'", line);
        String[] data = line.split(",");
        switch (data[0]) {
            case "$GPGGA" -> handleFixData(data);
            case "$GPGLL" -> handlePosition(data[1], data[2], data[3], data[4]);
        }
    }

    private void handleFixData(String[] data) {
        try {
            String satelliteString = data[7];
            if(satelliteString.contains(".")){
                satelliteString = satelliteString.substring(0, satelliteString.indexOf('.'));
            }
            numberOfSatellites = satelliteString.isEmpty() ? 0 : Integer.parseInt(satelliteString);
            if(numberOfSatellites == 0){
                logInfo("视野中没有卫星");
            }
            logDebug("正在使用的卫星数量: %d", numberOfSatellites);
            handlePosition(data[2], data[3], data[4], data[5]);
            handleAltitude(data[9]);
        } catch (Exception e) {
            logError("未知的NMEA句子: '%s'", String.join(",", data));
        }
    }

    private void handleAltitude(String altitudeString) {
        if(numberOfSatellites >= 3 && onNewAltitude != null && !altitudeString.isEmpty()){
            double altitude = Double.parseDouble(altitudeString);
            if(Math.abs(altitude - lastReportedAltitude) >= MIN_DISTANCE_M){
                lastReportedAltitude = altitude;
                logDebug("当前海拔高度: %.1f m", altitude);
                onNewAltitude.accept(altitude);
            }
        }
    }

    private void handlePosition(String lat, String northOrSouth, String lng, String eastOrWest){
        if(numberOfSatellites >=3 && onNewPosition != null){
            double latitude = 0;
            if (!lat.isEmpty()) {
                int degree = Integer.parseInt(lat.substring(0, 2));
                double minutes = Double.parseDouble(lat.substring(2));
                latitude = degree + (minutes / 60.0);
            }
            double longitude = 0;
            if (!lng.isEmpty()) {
                int degree = Integer.parseInt(lng.substring(0, 3));
                double minutes = Double.parseDouble(lng.substring(3));
                longitude = degree + (minutes / 60.0);
            }
            if (latitude != 0 && northOrSouth.equals("S")) {
                latitude = -latitude;
            }
            if (longitude != 0 && eastOrWest.equals("W")) {
                longitude = -longitude;
            }

            GeoPosition pos =  new GeoPosition(latitude, longitude);

            double moved = lastReportedPosition.distance(pos);
            logDebug("移动 %.2f m", moved);
            if(moved >= MIN_DISTANCE_M){
                logDebug("GPS：新位置： %s", pos.dms());
                lastReportedPosition = pos;
                onNewPosition.accept(pos);
            } else {
                logDebug("无明显移动");
            }
        }

    }

    public record GeoPosition(double latitude, double longitude) {

        public String dms() {
            return format(latitude, longitude);
        }

        /**
         * Just a simplified version of distance calculation. Doesn't take the different altitudes into account.
         *
         * @param otherPosition the position
         * @return distance in meter
         */
        public double distance(GeoPosition otherPosition){
            double lon1 = longitude;
            double lon2 = otherPosition.longitude;
            double lat1 = latitude;
            double lat2 = otherPosition.latitude;
            double theta = lon1 - lon2;

            return  rad2deg(Math.acos(Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta)))) * 60 * 1.1515 * 1609.344;
        }

        /**
         * converts decimal degrees to radians
         */
        private double deg2rad(double deg) {
            return (deg * Math.PI / 180.0);
        }

        /**
         * converts radians to decimal degrees
         */
         private double rad2deg(double rad) {
            return (rad * 180.0 / Math.PI);
        }

        private String format(double latitude, double longitude) {
            String latCompassDirection = (latitude > 0.0)  ? "N" : "S";
            String lonCompassDirection = (longitude > 0.0) ? "E" : "W";

            return String.format("%s%s, %s%s", getDMS(latitude), latCompassDirection, getDMS(longitude), lonCompassDirection);
        }

            private String getDMS(double value) {
                double absValue = Math.abs(value);
                int degree      = (int) absValue;
                int minutes     = (int) ((absValue - degree) * 60.0);
                double seconds  = (absValue - degree - minutes / 60.0) * 3600.0;

            return String.format("%d°%d′%s″", degree, minutes, String.format(Locale.ENGLISH, "%.4f", seconds));
        }
    }
}


