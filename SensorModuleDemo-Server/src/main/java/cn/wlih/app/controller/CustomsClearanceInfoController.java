package cn.wlih.app.controller;

import cn.wlih.app.dto.CustomsClearanceInfoDto;
import cn.wlih.app.model.CustomsClearanceInfo;
import cn.wlih.app.service.CustomsClearanceInfoService;
import cn.wlih.app.vo.CustomsClearanceInfoVo;
import cn.wlih.core.base.controller.MyBaseController;
import cn.wlih.core.base.model.ResponseResult;
import cn.wlih.core.myAnnotate.MyRequestBody;
import cn.wlih.core.util.MyModelUtil;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Tag(name = "海关清关信息管理")
@RequestMapping("/api/app/customsClearanceInfo")
public class CustomsClearanceInfoController extends MyBaseController<CustomsClearanceInfo, CustomsClearanceInfoDto, CustomsClearanceInfoVo> {

    @Autowired
    private CustomsClearanceInfoService customsClearanceInfoService;

    @ApiOperationSupport(order = 6)
    @Operation(summary = "通过运输信息ID查询清关信息")
    @PostMapping("/viewByTransportInfo")
    public ResponseResult<CustomsClearanceInfoVo> viewByTransportInfo(@MyRequestBody Long transportInfoId) {
        if (transportInfoId == null) {
            return ResponseResult.error("运输信息ID不能为空！");
        }
        CustomsClearanceInfo customsClearanceInfo = customsClearanceInfoService.viewByTransportInfo(transportInfoId);
        if (customsClearanceInfo == null) {
            return ResponseResult.error("该运输信息无相关清关信息！");
        }
        return ResponseResult.success(MyModelUtil.copyTo(customsClearanceInfo, CustomsClearanceInfoVo.class));
    }

}
