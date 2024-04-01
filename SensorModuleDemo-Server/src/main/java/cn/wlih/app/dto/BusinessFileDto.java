package cn.wlih.app.dto;

import cn.wlih.app.model.modelDbEnum.FileType;
import cn.wlih.core.base.dto.BaseModelDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Schema(title = "基础附件表")
@EqualsAndHashCode(callSuper = true)
public class BusinessFileDto extends BaseModelDto {

    @Schema(title = "文件名称")
    private String fileName;

    @Schema(title = "文件类型")
    private FileType fileType;

    @Schema(title = "文件大小")
    private Long fileSize;

    @Schema(title = "文件Key")
    private String fileKey;

}
