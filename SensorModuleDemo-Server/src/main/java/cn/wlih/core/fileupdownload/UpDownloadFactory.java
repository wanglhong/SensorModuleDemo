package cn.wlih.core.fileupdownload;

import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.EnumMap;
import java.util.Map;

/**
 * 文件上传下载工厂类
 *
 */
@Component
public class UpDownloadFactory {

    private final Map<UploadStoreTypeEnum, BaseUpDownload> upDownloaderMap = new EnumMap<>(UploadStoreTypeEnum.class);

    public static UploadFlagColumn getUploadFlagColumn(Class<?> modelDtoClass) {
        Field[] fields = modelDtoClass.getFields();
        UploadFlagColumn uploadFlagColumn = null;
        for (Field field : fields) {
            uploadFlagColumn = field.getAnnotation(UploadFlagColumn.class);
            if (uploadFlagColumn != null) {
                break;
            }
        }
        if (uploadFlagColumn == null) {
            throw (RuntimeException) new RuntimeException("文件上传失败，请检查实体类是否有@UploadFlagColumn注解！").initCause(null);
        }
        return uploadFlagColumn;
    }

    /**
     * 根据存储类型获取上传下载对象。
     * @param storeType 存储类型。
     * @return 匹配的上传下载对象。
     */
    public BaseUpDownload get(UploadStoreTypeEnum storeType) {
        BaseUpDownload upDownloader = upDownloaderMap.get(storeType);
        if (upDownloader == null) {
            throw new UnsupportedOperationException(
                    "The storeType [" + storeType.name() + "] isn't supported, please add dependency jar first.");
        }
        return upDownloader;
    }

    /**
     * 注册上传下载对象到工厂。
     *
     * @param storeType    存储类型。
     * @param upDownloader 上传下载对象。
     */
    public void registerUpDownloader(UploadStoreTypeEnum storeType, BaseUpDownload upDownloader) {
        if (storeType == null || upDownloader == null) {
            throw new IllegalArgumentException("The Argument can't be NULL.");
        }
        if (upDownloaderMap.containsKey(storeType)) {
            throw new UnsupportedOperationException(
                    "The storeType [" + storeType.name() + "] has been registered already.");
        }
        upDownloaderMap.put(storeType, upDownloader);
    }

}
