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
        return null;
    }
}
