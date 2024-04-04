package cn.wlih.core.fileupdownload;

import cn.wlih.app.model.BusinessFile;
import cn.wlih.core.fileupdownload.config.UpDownloadConfig;
import cn.wlih.core.sequence.wrapper.IdGeneratorWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 文件上传下载接口
 */
public abstract class BaseUpDownload {

    @Autowired
    protected UpDownloadConfig upDownloadConfig;
    @Autowired
    protected IdGeneratorWrapper idGeneratorWrapper;

    public String getSysPath() {
        return System.getProperty("user.dir");
    }

    public String buildFilePath(Class<?> fileModelClass) {
        String destinationPath = upDownloadConfig.getBucketName() + "/" + fileModelClass.getSimpleName() + "/";
        // 构建目标路径
        Path targetPath = Paths.get(getSysPath(), destinationPath);
        // 确保目标路径存在，如果不存在则逐层创建
        try {
            Files.createDirectories(targetPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return destinationPath;
    }

    /**
     * 上传文件
     * @param fileModelClass 文件模型类
     * @param multipartFile 文件
     * @return
     */
    public abstract UpDownloadResult upload(Class<?> fileModelClass, MultipartFile multipartFile);

    /**
     * 下载文件
     * @param fileName 文件名称
     * @param fileKey 文件KEY
     * @param suffix 文件后缀
     * @param response HttpServletResponse
     * @param fileModelClass 文件模型类
     */
    public abstract void download(String fileName, String fileKey, String suffix, HttpServletResponse response, Class<?> fileModelClass) throws IOException;

}
