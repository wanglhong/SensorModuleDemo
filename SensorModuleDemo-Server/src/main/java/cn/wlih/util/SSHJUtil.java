package cn.wlih.util;

import lombok.extern.slf4j.Slf4j;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.transport.verification.OpenSSHKnownHosts;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
import net.schmizz.sshj.common.IOUtils;
import net.schmizz.sshj.xfer.FileSystemFile;

import java.io.*;

/**
 * 描述：
 *
 * @author 王立宏
 * @date 2023/10/16 15:55
 * @path SensorModuleDemo-cn.wlih.util-SSHJUtil
 */
@Slf4j
public class SSHJUtil {

    private SSHConfig sshjConfig;

    public SSHJUtil() {
        this.sshjConfig = new SSHConfig();
    }

    public SSHJUtil(SSHConfig sshConfig) {
        this.sshjConfig = sshConfig;
    }

    /**
     * 执行命令
     */
    public String execCommand(String command) throws IOException {
        SSHClient sshClient = new SSHClient();
        String result = "无命令回执！";
        try {
            // 使用key进行连接
//            KeyProvider keyProvider = sshClient.loadKeys(sshjConfig.getKey(), null, null);
            sshClient.addHostKeyVerifier(new PromiscuousVerifier());
            sshClient.connect(sshjConfig.getIp(), sshjConfig.getPort());
            sshClient.authPassword(sshjConfig.getUserName(), sshjConfig.getPassword());
//            sshClient.authPublickey(sshjConfig.getUserName(), keyProvider);

            // 执行相关命令
            // 开启一个会话
            Session session = sshClient.startSession();
            command = "ls /root/sshTest";
            Session.Command cmd = session.exec(command);
            result = IOUtils.readFully(cmd.getInputStream()).toString();
            log.warn(result);
            InputStream inputStream = cmd.getInputStream();
            byte[] tmp = new byte[1024];
            while (inputStream.available() > 0) {
                int i = inputStream.read(tmp, 0, 1024);
                if (i < 0) break;
                String s = new String(tmp, 0, i);
//                if (s.indexOf("--More--") >= 0) {
//                    outputStream.write((" ").getBytes());
//                    outputStream.flush();
//                }
                log.warn(s);
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            sshClient.disconnect();
        }
        return result;
    }

    /**
     * 交互式SSH客户端
     */
    public void interactiveSSHClient(String command) throws IOException {
        final SSHClient sshClient = new SSHClient();
        Session session = null;
        Session.Shell shell = null;
        final File khFile = new File(OpenSSHKnownHosts.detectSSHDir(), "known_hosts");
        log.info("===================================================================");
        log.warn(khFile.getPath());
        log.info("===================================================================");
        Console console = System.console();
//        sshClient.addHostKeyVerifier(new ConsoleKnownHostsVerifier(khFile, console));
        sshClient.addHostKeyVerifier(new PromiscuousVerifier());
        sshClient.connect(sshjConfig.getIp(), sshjConfig.getPort());
        sshClient.authPassword(sshjConfig.getUserName(), sshjConfig.getPassword());
        try {

            session = sshClient.startSession();
            session.allocateDefaultPTY();
//            Session.Command cmd = session.exec(command);
//            String string = IOUtils.readFully(cmd.getInputStream()).toString();
//            cmd.join(5, TimeUnit.SECONDS);
//            log.warn(string);

            shell = session.startShell();
//            OutputStream outputStream = shell.getOutputStream();
//            outputStream.write(command.getBytes());
//            outputStream.flush();
//            outputStream.close();
//
//            InputStream inputStream = shell.getInputStream();
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//            String line;
//            System.out.println("\n\n\n\n");
//            System.out.println("----------------------------------------------------------------------");
//            while ((line = bufferedReader.readLine()) != null) {
//                System.out.println(line);
//            }
//            System.out.println("----------------------------------------------------------------------");
//            System.out.println("\n\n\n\n");
//            inputStream.close();

//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            outputStream.write(command.getBytes());
//            PrintStream out = System.out;
//            StreamCopier streamCopier = new StreamCopier(shell.getInputStream(), System.out, LoggerFactory.DEFAULT);
//            StreamCopier streamCopier1 = streamCopier.bufSize(shell.getLocalMaxPacketSize());
//            streamCopier1.spawn("stdout");
//
//            outputStream.close();
//
//            new StreamCopier(shell.getInputStream(), System.out, LoggerFactory.DEFAULT)
//                    .bufSize(shell.getLocalMaxPacketSize())
//                    .spawn("stdout");
//            new StreamCopier(shell.getErrorStream(), System.err, LoggerFactory.DEFAULT)
//                    .bufSize(shell.getLocalMaxPacketSize())
//                    .spawn("stderr");
//            new StreamCopier(System.in, shell.getOutputStream(), LoggerFactory.DEFAULT)
//                    .bufSize(shell.getRemoteMaxPacketSize())
//                    .copy();
            InputStream inputStream = shell.getInputStream();
            OutputStream outputStream = shell.getOutputStream();
            PrintWriter printWriter = new PrintWriter(outputStream);
            printWriter.println("cd /root/sshTest/SensorModuleDemoClient.jar");
            printWriter.println("ls");
            printWriter.println("java -jar SensorModuleDemoClient.jar");
            printWriter.println("exit");//为了结束本次交互
            printWriter.flush();//把缓冲区的数据强行输出
            byte[] tmp = new byte[1024];
            while(true) {
                while (inputStream.available() > 0) {
                    int i = inputStream.read(tmp, 0, 1024);
                    if (i < 0) break;
                    String s = new String(tmp, 0, i);
                    if (s.indexOf("--More--") >= 0) {
                        outputStream.write((" ").getBytes());
                        outputStream.flush();
                    }
                    log.warn(s);
                }
                if (shell.isOpen()) {
                    log.info(String.valueOf(shell.isOpen()));
//                    System.out.println("exit-status:" + shell.getstchannelShell.getExitStatus());
                    break;
                }
                try{Thread.sleep(1000);}catch(Exception e){}
            }



        } finally {
            if (shell != null) {
                shell.close();
            }
            if (session != null) {
                session.close();
            }
            sshClient.disconnect();
        }
    }

    /**
     * scp 上传文件
     */
    public void scpUploadFields(String localPath, String remotePath) throws IOException {
        final SSHClient sshClient = new SSHClient();
        try {
            sshClient.addHostKeyVerifier(new PromiscuousVerifier());
            sshClient.connect(sshjConfig.getIp(), sshjConfig.getPort());
            sshClient.authPassword(sshjConfig.getUserName(), sshjConfig.getPassword());

            sshClient.newSCPFileTransfer().upload(localPath,remotePath);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            sshClient.disconnect();
        }
    }

    /**
     * SFTP文件上传
     */
    public void sftpUploadFields(String localPath, String remotePath) throws IOException {
        final SSHClient sshClient = new SSHClient();
        SFTPClient sftpClient = null;
        try {
            sshClient.addHostKeyVerifier(new PromiscuousVerifier());
            sshClient.connect(sshjConfig.getIp(), sshjConfig.getPort());
            sshClient.authPassword(sshjConfig.getUserName(), sshjConfig.getPassword());

            sftpClient = sshClient.newSFTPClient();
            sftpClient.put(new FileSystemFile(localPath), remotePath);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (sftpClient != null) {
                sftpClient.close();
            }
            sshClient.disconnect();
        }
    }

    /**
     * SFTP文件下载
     */
    public void sftpDownloadFields(String localPath, String remotePath) throws IOException {
        final SSHClient sshClient = new SSHClient();
        SFTPClient sftpClient = null;
        try {
            sshClient.addHostKeyVerifier(new PromiscuousVerifier());
            sshClient.connect(sshjConfig.getIp(), sshjConfig.getPort());
            sshClient.authPassword(sshjConfig.getUserName(), sshjConfig.getPassword());

            sftpClient = sshClient.newSFTPClient();
            sftpClient.get(remotePath, new FileSystemFile(localPath));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (sftpClient != null) {
                sftpClient.close();
            }
            sshClient.disconnect();
        }
    }

}
