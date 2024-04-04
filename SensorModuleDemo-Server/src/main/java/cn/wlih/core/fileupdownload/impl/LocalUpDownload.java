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
import java.io.File;
import java.io.IOException;
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
        upDownloadResult.setFilePath(super.buildFilePath(fileModelClass));
        String originalFileName = multipartFile.getOriginalFilename();
        String fileExtension = null;
        if (originalFileName != null && originalFileName.contains(".")) {
            fileExtension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        }
        String newFileName = upDownloadResult.getFileKey() + "." + fileExtension;
        try {
            // 构建目标路径
            Path targetPath = Paths.get(System.getProperty("user.dir"), upDownloadResult.getFilePath());
            // 确保目标路径存在，如果不存在则逐层创建
            Files.createDirectories(targetPath);
            // 将文件保存到目标路径下
            multipartFile.transferTo(targetPath.resolve(newFileName).toFile());
            upDownloadResult.setFilePath(upDownloadResult.getFilePath() + newFileName);
        } catch (IOException e) {
            upDownloadResult.setSuccess(false);
            upDownloadResult.setMsg(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
        return upDownloadResult;
    }

}
