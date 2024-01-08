package cn.wlih.demo.Pi4j;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.serial.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Slf4j
public class SerialPortExample {

//    public void demo01() throws InterruptedException {
//        Context pi4j = Pi4J.newAutoContext();
//        Serial serial = pi4j.create(
//            Serial.newConfigBuilder(pi4j)
//                .use_9600_N81().dataBits_8().parity(Parity.NONE)
//                .stopBits(StopBits._1).flowControl(FlowControl.NONE)
//                .id("my-serial").device(SERIAL_ADDRESS)
//                .provider("pigpio-serial")
//                .build()
//        );
//        serial.open();
//        log.info("等到串口打开时");
//        while (!serial.isOpen()) {
//            Thread.sleep(250);
//        }
//
//        // 启动线程以处理来自串行端口的传入数据
//        SerialReader serialReader = new SerialReader(log, serial);
//        Thread serialReaderThread = new Thread(serialReader, "SerialReader");
//        serialReaderThread.setDaemon(true);
//        serialReaderThread.start();
//
//        while (serial.isOpen()) {
//            Thread.sleep(500);
//        }
//
//        serialReader.stopReading();
//    }

}

class SerialReader implements Runnable {

    private final Logger log;
    private final Serial serial;

    private boolean continueReading = true;

    public SerialReader(Logger log, Serial serial) {
        this.log = log;
        this.serial = serial;
    }

    public void stopReading() {
        continueReading = false;
    }

    @Override
    public void run() {
        // We use a buffered reader to handle the data received from the serial port
        BufferedReader br = new BufferedReader(new InputStreamReader(serial.getInputStream()));

        try {
            // Data from the GPS is recieved in lines
            String line = "";

            // Read data until the flag is false
            while (continueReading) {
                // First we need to check if there is data available to read.
                // The read() command for pigio-serial is a NON-BLOCKING call,
                // in contrast to typical java input streams.
                var available = serial.available();
                if (available > 0) {
                    for (int i = 0; i < available; i++) {
                        byte b = (byte) br.read();
                        if (b < 32) {
                            // All non-string bytes are handled as line breaks
                            if (!line.isEmpty()) {
                                // Here we should add code to parse the data to a GPS data object
                                log.info("Data: '" + line + "'");
                                line = "";
                            }
                        } else {
                            line += (char) b;
                        }
                    }
                } else {
                    Thread.sleep(10);
                }
            }
        } catch (Exception e) {
            log.error("从串行读取数据时出错: " + e.getMessage());
            System.out.println(e.getStackTrace());
        }
    }
}
