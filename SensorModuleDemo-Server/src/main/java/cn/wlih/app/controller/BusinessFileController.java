package cn.wlih.app.controller;

import cn.wlih.app.dto.BusinessFileDto;
import cn.wlih.app.model.BusinessFile;
import cn.wlih.app.service.BusinessFileService;
import cn.wlih.app.vo.BusinessFileVo;
import cn.wlih.core.base.controller.MyBaseController;
import cn.wlih.core.base.model.ResponseResult;
import cn.wlih.core.fileupdownload.*;
import cn.wlih.core.myAnnotate.MyRequestBody;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;

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
    public ResponseResult<BusinessFileVo> add(BusinessFileDto modelDto) {
        return ResponseResult.error("附件表不支持新增接口，请使用文件上传接口！");
    }

    /**
     * 基础新增接口
     *
     * @param modelDto 实体
     */
    public ResponseResult<BusinessFileVo> upload(
            @Parameter(description = "新增的对象信息") @MyRequestBody BusinessFileDto modelDto, MultipartFile file) {
        if (modelDto == null) {
            return ResponseResult.error("文件信息对象不能为空！");
        }
        if (modelDto.getFileName() == null) {
            return ResponseResult.error("文件名称不能为空！");
        }
        if (modelDto.getFileType() == null) {
            return ResponseResult.error("文件类型（后缀名）不能为空！");
        }
        if (file == null) {
            return ResponseResult.error("文件不能为空！");
        }
        UploadFlagColumn uploadFlagColumn = UpDownloadFactory.getUploadFlagColumn(modelDto.getClass());
        BaseUpDownload baseUpDownload = upDownloadFactory.get(uploadFlagColumn.storeType());
        UpDownloadResult upload = baseUpDownload.upload(modelDto.getClass(), file);
        return super.add(modelDto);
    }

}
