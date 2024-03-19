package cn.wlih.app.controller;

import cn.wlih.app.dto.GoodsDto;
import cn.wlih.app.model.Goods;
import cn.wlih.app.service.GoodsService;
import cn.wlih.app.vo.GoodsVo;
import cn.wlih.core.base.controller.MyBaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Tag(name = "货物信息管理")
@RequestMapping("/api/app/goods")
public class GoodsController extends MyBaseController<Goods, GoodsDto, GoodsVo> {

    @Autowired
    private GoodsService goodsService;

}
