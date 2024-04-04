package cn.wlih.app.controller;

import cn.wlih.app.dto.BusinessFileDto;
import cn.wlih.app.model.BusinessFile;
import cn.wlih.app.model.modelDbEnum.FileType;
import cn.wlih.app.service.BusinessFileService;
import cn.wlih.app.vo.BusinessFileVo;
import cn.wlih.core.base.controller.MyBaseController;
import cn.wlih.core.base.model.ResponseResult;
import cn.wlih.core.fileupdownload.*;
import cn.wlih.core.myAnnotate.MyRequestBody;
import cn.wlih.core.util.MyModelUtil;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@RestController
@Tag(name = "基础附件管理")
@RequestMapping("/api/app/businessFile")
public class BusinessFileController extends MyBaseController<BusinessFile, BusinessFileDto, BusinessFileVo> {

    @Autowired
    private BusinessFileService businessFileService;
    @Autowired
    private UpDownloadFactory upDownloadFactory;

    /**
     * 基础新增接口
     *
     * @param modelDto 实体
     */
    @Override
    public ResponseResult<BusinessFileVo> add(@Parameter(description = "新增的对象信息") @MyRequestBody BusinessFileDto modelDto) {
        return ResponseResult.error("附件表不支持新增接口，请使用文件上传接口！");
    }

    /**
     * 基础新增接口
     */
    @ApiOperationSupport(order = 6)
    @Operation(summary = "文件上传接口")
    @PostMapping("/upload")
    public ResponseResult<BusinessFileVo> upload(
            @RequestParam("uploadFile") MultipartFile uploadFile) {
        if (uploadFile == null) {
            return ResponseResult.error("文件不能为空！");
        }
        String originalFilename = uploadFile.getOriginalFilename();
        BusinessFile businessFile = new BusinessFile();
        businessFile.setFileName(originalFilename);
        businessFile.setFileType(FileType.getFileTypeByFileName(originalFilename));
        businessFile.setFileSize(uploadFile.getSize());

        UploadFlagColumn uploadFlagColumn = UpDownloadFactory.getUploadFlagColumn(BusinessFile.class);
        BaseUpDownload baseUpDownload = upDownloadFactory.get(uploadFlagColumn.storeType());
        UpDownloadResult upload = baseUpDownload.upload(BusinessFile.class, uploadFile);
        if (!upload.getSuccess()) {
            return ResponseResult.error(upload.getMsg());
        }
        businessFile.setFileKey(upload.getFileKey());
        businessFile.setFileSize(uploadFile.getSize());
        businessFile = businessFileService.add(businessFile);
        return ResponseResult.success(MyModelUtil.copyTo(businessFile, BusinessFileVo.class));
    }

    @ApiOperationSupport(order = 7)
    @Operation(summary = "文件下载接口")
    @PostMapping("/download")
    public void download(
            @Parameter(description = "文件ID") Long id,
            HttpServletResponse response) throws IOException {
        BusinessFile businessFile = businessFileService.getById(id);
        if (businessFile == null) {
            ResponseResult.output(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        UploadFlagColumn uploadFlagColumn = UpDownloadFactory.getUploadFlagColumn(BusinessFile.class);
        BaseUpDownload baseUpDownload = upDownloadFactory.get(uploadFlagColumn.storeType());
        try {
            baseUpDownload.download(businessFile.getFileName(), businessFile.getFileKey(), businessFile.getFileType().getSuffix(), response, BusinessFile.class);
        } catch (Exception e) {
            ResponseResult.output(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ResponseResult.error(e.getMessage()));
        }
    }

}
