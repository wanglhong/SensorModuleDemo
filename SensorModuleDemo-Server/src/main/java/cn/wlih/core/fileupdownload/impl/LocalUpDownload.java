package cn.wlih.core.fileupdownload.impl;

import cn.wlih.core.fileupdownload.BaseUpDownload;
import cn.wlih.core.fileupdownload.UpDownloadFactory;
import cn.wlih.core.fileupdownload.UpDownloadResult;
import cn.wlih.core.fileupdownload.UploadStoreTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 存储本地文件的上传下载实现类
 */
@Slf4j
@Component
public class LocalUpDownload extends BaseUpDownload {

    @Autowired
    private UpDownloadFactory factory;

    @PostConstruct
    public void doRegister() {
        factory.registerUpDownloader(UploadStoreTypeEnum.LOCAL_SYSTEM, this);
    }

    /**
     * 上传文件
     *
     * @param fileModelClass 文件模型类
     * @param multipartFile
     * @return
     */
    @Override
    public UpDownloadResult upload(Class<?> fileModelClass, MultipartFile multipartFile) {
        UpDownloadResult upDownloadResult = new UpDownloadResult();
        upDownloadResult.setFileKey(idGeneratorWrapper.nextStringId());
        String originalFileName = multipartFile.getOriginalFilename();
        String fileExtension = null;
        if (originalFileName != null && originalFileName.contains(".")) {
            fileExtension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        }
        String newFileName = upDownloadResult.getFileKey() + "." + fileExtension;
        try {
            // 将文件保存到目标路径下
            multipartFile.transferTo(Paths.get(super.getSysPath(), super.buildFilePath(fileModelClass)).resolve(newFileName).toFile());
            upDownloadResult.setFilePath(upDownloadResult.getFilePath() + newFileName);
        } catch (IOException e) {
            upDownloadResult.setSuccess(false);
            upDownloadResult.setMsg(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
        return upDownloadResult;
    }

    @Override
    public void download(String fileName, String fileKey, String suffix, HttpServletResponse response, Class<?> fileModelClass) throws IOException {
        Path path = Paths.get(super.getSysPath(), super.buildFilePath(fileModelClass)).resolve(fileKey + "." + suffix);
        if (!Files.exists(path)) {
            throw new IOException("文件不存在！");
        }
        File file = path.toFile();
        if (!file.exists()) {
            log.warn("Download file [" + (super.buildFilePath(fileModelClass)) + fileKey + "." + suffix + "] failed, no file found!");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        //设置编码格式，防止下载的文件内乱码
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-type", "application/octet-stream;charset=UTF-8");
        response.setHeader("content-disposition", "attachment;filename=" + java.net.URLEncoder.encode(fileName.trim(), "UTF-8"));
        response.setHeader("my-test", "my-test-data");
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.addHeader("Content-Length", String.valueOf(file.length()));
        byte[] buff = new byte[2048];
        try (OutputStream os = response.getOutputStream();
             BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, i);
                os.flush();
                i = bis.read(buff);
            }
        } catch (IOException e) {
            log.error("Failed to call LocalUpDownloader.doDownload", e);
        }


//        //设置编码格式，防止下载的文件内乱码
//        response.setCharacterEncoding("UTF-8");
//        //获取路径文件对象
//        String realFileName = file.getName();
//        //设置响应头类型，这里可以根据文件类型设置，text/plain、application/vnd.ms-excel等
//        response.setHeader("content-type", "application/octet-stream;charset=UTF-8");
//        response.setContentType("application/octet-stream;charset=UTF-8");
//        //如果不设置响应头大小，可能报错：“Excel 已完成文件级验证和修复。此工作簿的某些部分可能已被修复或丢弃”
//        response.addHeader("Content-Length", String.valueOf(file.length()));
//        try{
//            //Content-Disposition的作用：告知浏览器以何种方式显示响应返回的文件，用浏览器打开还是以附件的形式下载到本地保存
//            //attachment表示以附件方式下载   inline表示在线打开   "Content-Disposition: inline; filename=文件名.mp3"
//            // filename表示文件的默认名称，因为网络传输只支持URL编码的相关支付，因此需要将文件名URL编码后进行传输,前端收到后需要反编码才能获取到真正的名称
//            response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(realFileName.trim(), "UTF-8"));
//        } catch (UnsupportedEncodingException e1) {
//            e1.printStackTrace();
//        }
//        //初始化文件流字节缓存
//        byte[] buff = new byte[1024];
//        try {
//            //开始写入
//            OutputStream os = response.getOutputStream();
//            //写入完成，创建文件流
//            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
//            // bis.read(data)：将字符读入数组。在某个输入可用、发生I/O错误或者已到达流的末尾前，此方法一直阻塞。
//            // 读取的字符数，如果已到达流的末尾，则返回 -1
//            int i = bis.read(buff);
//            while (i != -1) {
//                os.write(buff, 0, buff.length);
//                os.flush();
//                i = bis.read(buff);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
    }
}
