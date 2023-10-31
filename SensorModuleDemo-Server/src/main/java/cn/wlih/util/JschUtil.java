package cn.wlih.util;

import com.jcraft.jsch.ChannelShell;
import lombok.extern.slf4j.Slf4j;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.*;

/**
 * 描述：
 *
 * @author 王立宏
 * @date 2023/10/17 14:46
 * @path SensorModuleDemo-cn.wlih.util-JschUtil
 */
@Slf4j
public class JschUtil {

    private SSHConfig sshConfig;

    public JschUtil() {
        this.sshConfig = new SSHConfig();
    }

    public JschUtil(SSHConfig sshConfig) {
        this.sshConfig = sshConfig;
    }

    public void executeRemoteCommand(String... command) throws Exception {
        JSch jsch = new JSch();
        Session session = jsch.getSession(sshConfig.getUserName(), sshConfig.getIp(), sshConfig.getPort());
        session.setPassword(sshConfig.getPassword());
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();

        ChannelShell channelShell = (ChannelShell) session.openChannel("shell");
        InputStream inputStream = channelShell.getInputStream();//从远端到达的数据  都能从这个流读取到
        channelShell.setPty(true);
        channelShell.connect();

        OutputStream outputStream = channelShell.getOutputStream();//写入该流的数据  都将发送到远程端
        //使用PrintWriter 就是为了使用println 这个方法
        //好处就是不需要每次手动给字符加\n
        PrintWriter printWriter = new PrintWriter(outputStream);
        for (String com : command) {
            printWriter.println(com);
        }
        printWriter.println("exit");//为了结束本次交互
        printWriter.flush();//把缓冲区的数据强行输出

        /**
         shell管道本身就是交互模式的。要想停止，有两种方式：
         一、人为的发送一个exit命令，告诉程序本次交互结束
         二、使用字节流中的available方法，来获取数据的总大小，然后循环去读。
         为了避免阻塞
         */
        byte[] tmp = new byte[1024];
        while (true) {
            while(inputStream.available() > 0){
                int i = inputStream.read(tmp, 0, 1024);
                if(i < 0) break;
                String s = new String(tmp, 0, i);
                if(s.indexOf("--More--") >= 0){
                    outputStream.write((" ").getBytes());
                    outputStream.flush();
                }
                log.info(s);
            }
            if(channelShell.isClosed()){
                log.info("exit-status:"+channelShell.getExitStatus());
                break;
            }
            try{Thread.sleep(1000);}catch(Exception e){}

        }
        outputStream.close();
        inputStream.close();
        channelShell.disconnect();
        session.disconnect();
    }

}
