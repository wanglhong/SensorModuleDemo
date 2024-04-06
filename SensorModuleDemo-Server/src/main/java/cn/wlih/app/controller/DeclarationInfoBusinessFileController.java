package cn.wlih.app.controller;

import cn.wlih.app.dto.ClearanceInfoBusinessFileDto;
import cn.wlih.app.dto.DeclarationInfoBusinessFileDto;
import cn.wlih.app.model.ClearanceInfoBusinessFile;
import cn.wlih.app.model.DeclarationInfoBusinessFile;
import cn.wlih.app.service.DeclarationInfoBusinessFileService;
import cn.wlih.app.vo.DeclarationInfoBusinessFileVo;
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
@Tag(name = "报关附件管理")
@RequestMapping("/api/app/declarationInfoBusinessFile")
public class DeclarationInfoBusinessFileController extends MyBaseController<DeclarationInfoBusinessFile, DeclarationInfoBusinessFileDto, DeclarationInfoBusinessFileVo> {

    @Autowired
    private DeclarationInfoBusinessFileService declarationInfoBusinessFileService;

    @ApiOperationSupport(order = 6)
    @Operation(summary = "删除-通过[declarationInfoId、businessFileId]")
    @PostMapping("/deleteFile")
    public ResponseResult<Void> deleteFile(
            @Parameter(description = "删除的对象ID") @MyRequestBody DeclarationInfoBusinessFileDto declarationInfoBusinessFileDto) {
        if (declarationInfoBusinessFileDto == null) {
            return ResponseResult.error("缺少必要的参数[declarationInfoId、businessFileId]");
        }
        if (declarationInfoBusinessFileDto.getDeclarationInfoId() == null) {
            return ResponseResult.error("缺少必要的参数[declarationInfoId]");
        }
        if (declarationInfoBusinessFileDto.getBusinessFileId() == null) {
            return ResponseResult.error("缺少必要的参数[businessFileId]");
        }
        DeclarationInfoBusinessFile declarationInfoBusinessFile = MyModelUtil.copyTo(declarationInfoBusinessFileDto, DeclarationInfoBusinessFile.class);
        LambdaQueryWrapper<DeclarationInfoBusinessFile> queryWrapper = new LambdaQueryWrapper<DeclarationInfoBusinessFile>()
                .eq(DeclarationInfoBusinessFile::getDeclarationInfoId, declarationInfoBusinessFile.getDeclarationInfoId())
                .eq(DeclarationInfoBusinessFile::getBusinessFileId, declarationInfoBusinessFile.getBusinessFileId());
        declarationInfoBusinessFile = declarationInfoBusinessFileService.getOne(queryWrapper);
        if (declarationInfoBusinessFile == null) {
            return ResponseResult.error("数据不存在，无法删除！");
        }
        if (declarationInfoBusinessFileService.remove(queryWrapper)) {
            return ResponseResult.success();
        }
        return ResponseResult.error("删除失败，请重试！");
    }

}
