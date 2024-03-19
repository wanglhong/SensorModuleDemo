package cn.wlih.app.service.impl;

import cn.wlih.app.dao.TransportInfoTurnoverBoxGoodsMapper;
import cn.wlih.app.model.TransportInfoTurnoverBoxGoods;
import cn.wlih.app.service.TransportInfoTurnoverBoxGoodsService;
import cn.wlih.core.base.mapper.MyBaseMapper;
import cn.wlih.core.base.service.impl.MyBaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service("transportInfoTurnoverBoxGoodsService")
public class TransportInfoTurnoverBoxGoodsServiceImpl extends MyBaseServiceImpl<TransportInfoTurnoverBoxGoods> implements TransportInfoTurnoverBoxGoodsService {

    @Autowired
    private TransportInfoTurnoverBoxGoodsMapper transportInfoTurnoverBoxGoodsMapper;

    /**
     * @return 当前Service的主表Mapper对象
     */
    @Override
    protected MyBaseMapper<TransportInfoTurnoverBoxGoods> mapper() {
        return transportInfoTurnoverBoxGoodsMapper;
    }

}
