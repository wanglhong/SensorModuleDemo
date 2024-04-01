package cn.wlih.app.model.modelDbEnum;

import cn.wlih.core.myAnnotate.ClassComment;
import cn.wlih.core.myAnnotate.VariableComment;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@ClassComment("文件类型")
public enum FileType {

    JPG("jpg", "图片"),
    JPEG("jpeg", "图片"),
    PNG("png", "图片"),
    GIF("gif", "图片"),
    BMP("bmp", "图片"),
    TIF("tif", "图片"),
    PDF("pdf", "Pdf文件"),
    XLS("xls", "Excel文件"),
    XLSX("xlsx", "Excel文件"),
    DOC("doc", "Word文件"),
    DOCX("docx", "Word文件"),
    PPT("ppt", "Ppt文件"),
    PPTX("pptx", "Ppt文件"),
    TXT("txt", "文本文件"),
    MD("md", "markdown文件"),
    ZIP("zip", "压缩文件"),
    RAR("rar", "压缩文件"),
    Z7("7z", "压缩文件"),
    MP3("mp3", "音频文件"),
    MP4("mp4", "视频文件");

    @EnumValue
    @JsonValue
    private final String suffix;

    private final String display;

}
