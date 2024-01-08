package cn.wlih.demo.Pi4j.gpsDemo;

import com.pi4j.context.Context;
import com.pi4j.io.serial.FlowControl;
import com.pi4j.io.serial.Parity;
import com.pi4j.io.serial.Serial;
import com.pi4j.io.serial.StopBits;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.function.Consumer;

/**
 *
 */
public class SerialDevice extends Component {
    /**
     * PI4J 串行
     */
    private final Serial serial;

    private final Consumer<String> onNewData;

    private boolean continueReading = false;

    private Thread serialReaderThread;

    public SerialDevice(Context pi4j, Consumer<String> onNewData){
        serial = pi4j.create(Serial.newConfigBuilder(pi4j)
                .use_9600_N81()
                .dataBits_8()
                .parity(Parity.NONE)
                .stopBits(StopBits._1)
                .flowControl(FlowControl.NONE)
                .id("my-serial")
                .device("/dev/ttyS0")
                .build());
        this.onNewData = onNewData;
        //TODO: 检查这是否真的有必要
        serial.open();
        // 等到串口打开后
        while (!serial.isOpen()) {
            delay(Duration.ofMillis(250));
        }
    }

    @Override
    public void reset() {
        stopReading();
        serial.close();

        super.reset();
    }

    public void stopReading() {
        continueReading = false;
        serialReaderThread = null;
    }

    public void startReading(){
        if(continueReading){
            return;
        }
        continueReading = true;
        serialReaderThread = new Thread(() -> listenToSerialPort(), "SerialReader");
        serialReaderThread.setDaemon(true);
        serialReaderThread.start();
    }

    private void listenToSerialPort() {
        // 我们使用缓冲读卡器来处理从串行端口接收的数据
        BufferedReader br = new BufferedReader(new InputStreamReader(serial.getInputStream()));

        try {
            // 来自 GPS 的数据是成行接收的
            StringBuilder line = new StringBuilder();

            // 读取数据，直到标志为 false
            while (continueReading) {
                // 首先，我们需要检查是否有可供读取的数据。
                // pi-gpio-serial 的 read（） 命令是一个非阻塞调用，与典型的 java 输入流不同。
                var available = serial.available();
                if (available > 0) {
                    for (int i = 0; i < available; i++) {
                        byte b = (byte) br.read();
                        if (b < 32) {
                            // 所有非字符串字节都作为换行符处理
                            if (line.length() > 0) {
                                // 在这里，我们应该添加代码以将数据解析为 GPS 数据对象
                                onNewData.accept(line.toString());
                                line = new StringBuilder();
                            }
                        } else {
                            line.append((char) b);
                        }
                    }
                } else {
                    Thread.sleep(100);
                }
            }
        } catch (Exception e) {
            logException("从串行读取数据时出错: ", e);
            e.printStackTrace();
        }
    }
}
