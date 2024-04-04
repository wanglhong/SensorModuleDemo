package cn.wlih.core.fileupdownload;

import lombok.Data;

@Data
public class UpDownloadResult {

    private Boolean success = true;

    private String msg = "SUCCESS";

    private String fileKey;

    private String filePath;

}
