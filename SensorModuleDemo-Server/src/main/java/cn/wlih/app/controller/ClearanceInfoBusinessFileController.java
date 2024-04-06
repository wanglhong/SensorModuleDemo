package cn.wlih.app.controller;

import cn.wlih.app.dto.ClearanceInfoBusinessFileDto;
import cn.wlih.app.model.ClearanceInfoBusinessFile;
import cn.wlih.app.service.ClearanceInfoBusinessFileService;
import cn.wlih.app.vo.ClearanceInfoBusinessFileVo;
import cn.wlih.core.base.controller.MyBaseController;
import cn.wlih.core.base.model.ResponseResult;
import cn.wlih.core.myAnnotate.MyRequestBody;
import cn.wlih.core.util.MyModelUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
    @Operation(summary = "删除-通过[clearanceInfoId、businessFileId]")
    @PostMapping("/deleteFile")
    public ResponseResult<Void> deleteFile(
            @Parameter(description = "删除的对象ID") @MyRequestBody ClearanceInfoBusinessFileDto clearanceInfoBusinessFileDto) {
        if (clearanceInfoBusinessFileDto == null) {
            return ResponseResult.error("缺少必要的参数[clearanceInfoId、businessFileId]");
        }
        if (clearanceInfoBusinessFileDto.getClearanceInfoId() == null) {
            return ResponseResult.error("缺少必要的参数[clearanceInfoId]");
        }
        if (clearanceInfoBusinessFileDto.getBusinessFileId() == null) {
            return ResponseResult.error("缺少必要的参数[businessFileId]");
        }
        ClearanceInfoBusinessFile clearanceInfoBusinessFile = MyModelUtil.copyTo(clearanceInfoBusinessFileDto, ClearanceInfoBusinessFile.class);
        LambdaQueryWrapper<ClearanceInfoBusinessFile> queryWrapper = new LambdaQueryWrapper<ClearanceInfoBusinessFile>()
                .eq(ClearanceInfoBusinessFile::getClearanceInfoId, clearanceInfoBusinessFile.getClearanceInfoId())
                .eq(ClearanceInfoBusinessFile::getBusinessFileId, clearanceInfoBusinessFile.getBusinessFileId());
        clearanceInfoBusinessFile = clearanceInfoBusinessFileService.getOne(queryWrapper);
        if (clearanceInfoBusinessFile == null) {
            return ResponseResult.error("数据不存在，无法删除！");
        }
        if (clearanceInfoBusinessFileService.remove(queryWrapper)) {
            return ResponseResult.success();
        }
        return ResponseResult.error("删除失败，请重试！");
    }

}
