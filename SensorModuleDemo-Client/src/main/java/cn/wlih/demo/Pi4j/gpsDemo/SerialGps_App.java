package cn.wlih.demo.Pi4j.gpsDemo;

import com.pi4j.context.Context;

import java.time.Duration;

public class SerialGps_App extends Component {

    public void execute(Context pi4j) {
        System.out.println("GPS 演示开始");

        SerialGps gps = new SerialGps(pi4j,
                (pos) -> System.out.printf("位置: %.6f, %.6f; DMS: %s%n", pos.latitude(), pos.longitude(), pos.dms() ),
                (alt) -> System.out.printf("高度: %.1fm%n", alt));

        gps.start();

        //提供 15 秒的职位
        delay(Duration.ofSeconds(15));

        gps.reset();

        System.out.println("GPS演示完成");
    }

}
