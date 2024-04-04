package cn.wlih.core.fileupdownload;

import cn.wlih.core.fileupdownload.config.UpDownloadConfig;
import cn.wlih.core.sequence.wrapper.IdGeneratorWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传下载接口
 */
public abstract class BaseUpDownload {

    @Autowired
    protected UpDownloadConfig upDownloadConfig;
    @Autowired
    protected IdGeneratorWrapper idGeneratorWrapper;

    public String buildFilePath(Class<?> fileModelClass) {
        return upDownloadConfig.getBucketName() + "/" + fileModelClass.getSimpleName() + "/";
    }

    /**
     * 上传文件
     * @param fileModelClass 文件模型类
     * @param multipartFile
     * @return
     */
    public abstract UpDownloadResult upload(Class<?> fileModelClass, MultipartFile multipartFile);

}
