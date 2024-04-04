package cn.wlih.app.controller;

import cn.wlih.app.dto.TransportInfoTurnoverBoxGoodsDto;
import cn.wlih.app.model.TransportInfoTurnoverBoxGoods;
import cn.wlih.app.service.TransportInfoTurnoverBoxGoodsService;
import cn.wlih.app.vo.TransportInfoTurnoverBoxGoodsVo;
import cn.wlih.core.base.controller.MyBaseController;
import cn.wlih.core.base.model.ResponseResult;
import cn.wlih.core.myAnnotate.MyRequestBody;
import cn.wlih.core.object.TokenData;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Tag(name = "运输信息-周转箱-货物关联管理")
@RequestMapping("/api/app/transportInfoTurnoverBoxGoods")
public class TransportInfoTurnoverBoxGoodsController extends MyBaseController<TransportInfoTurnoverBoxGoods, TransportInfoTurnoverBoxGoodsDto, TransportInfoTurnoverBoxGoodsVo> {

    @Autowired
    private TransportInfoTurnoverBoxGoodsService transportInfoTurnoverBoxGoodsService;

    /**
     * 基础新增接口
     *
     * @param modelDto 实体
     */
    @Override
    public ResponseResult<TransportInfoTurnoverBoxGoodsVo> add(
            @Parameter(description = "新增的对象信息") @MyRequestBody TransportInfoTurnoverBoxGoodsDto modelDto) {
        modelDto.setGoodsToBoxUserId(TokenData.takeFromRequest().getUserId());
        return super.add(modelDto);
    }

    /**
     * 基础修改接口
     *
     * @param modelDto
     */
    @Override
    public ResponseResult<Void> updateById(
            @Parameter(description = "修改的对象信息", required = true) @MyRequestBody TransportInfoTurnoverBoxGoodsDto modelDto) {
        if (modelDto.getId() == null) {
            return ResponseResult.error("ID不能为空");
        }
        TransportInfoTurnoverBoxGoods originModel = transportInfoTurnoverBoxGoodsService.getById(modelDto.getId());
        if (originModel == null) {
            return ResponseResult.error("数据不存在！");
        }
        if (originModel.getBoxToTransportEquipmentUserId() == null) {
            modelDto.setBoxToTransportEquipmentUserId(TokenData.takeFromRequest().getUserId());
        }
        return super.updateById(modelDto);
    }
}
