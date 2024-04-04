package cn.wlih.core.fileupdownload.config;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class UpDownloadConfig {

    private String bucketName = "SensorModuleDevFile";

}
