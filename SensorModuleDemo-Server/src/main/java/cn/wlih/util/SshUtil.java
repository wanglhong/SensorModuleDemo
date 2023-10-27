package cn.wlih.util;

import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

/**
 * 描述：
 *
 * @author 王立宏
 * @date 2023/10/27 23:40
 * @path SensorModuleDemo-cn.wlih.util-SshUtil
 */
@Slf4j
@Component
public class SshUtil {
    
    private static final SSHConfig SSH_CONFIG = new SSHConfig();
    private Session session;
    private ChannelShell channelShell;

//    {
//        getShell();
//    }

    public void getShell() throws JSchException {
        if (this.channelShell != null && this.channelShell.isConnected()) return;
        if (this.session != null && !this.session.isConnected()) {
            this.session.connect();
        }
        if (this.session == null) {
            // 重新获取 session
            JSch jsch = new JSch();
            this.session = jsch.getSession(
                    SSH_CONFIG.getUserName(), SSH_CONFIG.getIp(), SSH_CONFIG.getPort());
            this.session.setPassword(SSH_CONFIG.getPassword());
            this.session.setConfig("StrictHostKeyChecking", "no");
            this.session.connect();
        }
        this.channelShell = (ChannelShell) this.session.openChannel("shell");
//        if (this.channelShell.isClosed()) getShell();
    }

    /**
     * 关闭SSH
     *
     * @author 王立宏
     * @date 2023/10/28 02:13
     */
    public void close() {
        if (this.channelShell != null) {
            if (this.channelShell.isConnected()) this.channelShell.disconnect();
            this.channelShell = null;
        }
        if (this.session == null) return;
        if (this.session.isConnected()) this.session.disconnect();
        this.session = null;
    }

    public String executeRemoteCommand(List<String> commands) {
        if (commands == null || commands.size() < 1) return "命令参数（commands）不能为空！";
        if (this.channelShell == null) {
            try {
                getShell();
            } catch (JSchException e) {
                log.error(e.getMessage());
                return e.getMessage().contains("Connection timed out") ? "连接超时，请重试！" : e.getMessage();
            }
        }
        StringBuilder resultMsg = new StringBuilder();
        if (this.channelShell.isClosed()) {
            try {
                getShell();
            } catch (JSchException e) {
                log.error(e.getMessage());
                return e.getMessage().contains("Connection timed out") ? "连接超时，请重试！" : e.getMessage();
            }
        }

        try {
            InputStream inputStream = this.channelShell.getInputStream();//从远端到达的数据  都能从这个流读取到
            this.channelShell.setPty(true);
            this.channelShell.connect();

            OutputStream outputStream = this.channelShell.getOutputStream();//写入该流的数据  都将发送到远程端
            //使用PrintWriter 就是为了使用println 这个方法
            //好处就是不需要每次手动给字符加\n
            PrintWriter printWriter = new PrintWriter(outputStream);
            for (String command : commands) {
                printWriter.println(command);
            }
            printWriter.println("exit");
            printWriter.flush();

            byte[] tmp = new byte[1024];
            while(true){
                while(inputStream.available() > 0){
                    int i = inputStream.read(tmp, 0, 1024);
                    if(i < 0) break;
                    String s = new String(tmp, 0, i);
                    if(s.indexOf("--More--") >= 0){
                        outputStream.write((" ").getBytes());
                        outputStream.flush();
                    }
                    log.info(s);
                    resultMsg.append(s);
                }
                if(channelShell.isClosed()){
                    log.info("exit-status:"+channelShell.getExitStatus());
                    break;
                }
                try{ Thread.sleep(500); } catch (Exception e){}
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException | JSchException e) {
            throw new RuntimeException(e);
        }
        return resultMsg.toString();
    }

}
