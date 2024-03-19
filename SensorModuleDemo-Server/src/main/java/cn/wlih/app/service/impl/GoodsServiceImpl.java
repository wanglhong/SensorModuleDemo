package cn.wlih.app.service.impl;

import cn.wlih.app.dao.GoodsMapper;
import cn.wlih.app.model.Goods;
import cn.wlih.app.service.GoodsService;
import cn.wlih.core.base.mapper.MyBaseMapper;
import cn.wlih.core.base.service.impl.MyBaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service("goodsService")
public class GoodsServiceImpl extends MyBaseServiceImpl<Goods> implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    /**
     * @return 当前Service的主表Mapper对象
     */
    @Override
    protected MyBaseMapper<Goods> mapper() {
        return goodsMapper;
    }

}
