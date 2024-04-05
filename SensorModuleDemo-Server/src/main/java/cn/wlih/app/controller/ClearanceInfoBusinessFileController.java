package cn.wlih.app.controller;

import cn.wlih.app.dto.ClearanceInfoBusinessFileDto;
import cn.wlih.app.model.ClearanceInfoBusinessFile;
import cn.wlih.app.service.ClearanceInfoBusinessFileService;
import cn.wlih.app.vo.ClearanceInfoBusinessFileVo;
import cn.wlih.core.base.controller.MyBaseController;
import cn.wlih.core.base.model.ResponseResult;
import cn.wlih.core.myAnnotate.MyRequestBody;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Tag(name = "清关附件管理")
@RequestMapping("/api/app/clearanceInfoBusinessFile")
public class ClearanceInfoBusinessFileController extends MyBaseController<ClearanceInfoBusinessFile, ClearanceInfoBusinessFileDto, ClearanceInfoBusinessFileVo> {

    @Autowired
    private ClearanceInfoBusinessFileService clearanceInfoBusinessFileService;

    @ApiOperationSupport(order = 6)
    @Operation(summary = "基础 - 删除接口（通过ID）")
    @PostMapping("/deleteFile")
    public ResponseResult<Void> deleteFile(@Parameter(description = "删除的对象ID") @MyRequestBody ClearanceInfoBusinessFile clearanceInfoBusinessFile) {
        // TODO clearanceInfoBusinessFile 1776184215423946752
        return ResponseResult.error("修改失败，请重试！");
    }

}
