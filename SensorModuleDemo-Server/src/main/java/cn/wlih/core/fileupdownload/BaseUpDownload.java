package cn.wlih.core.fileupdownload;

import cn.wlih.app.dto.BusinessFileDto;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传下载接口
 */
public abstract class BaseUpDownload {

    /**
     * 上传文件
     * @param fileModelClass 文件模型类
     * @param multipartFile
     * @return
     */
    public abstract UpDownloadResult upload(Class<?> fileModelClass, MultipartFile multipartFile);

}
