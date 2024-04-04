package cn.wlih.core.fileupdownload;

import lombok.Data;

@Data
public class UpDownloadResult {

    private Boolean Success;

    private String fileKey;

    private String filePath;

}
