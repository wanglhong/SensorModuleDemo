package cn.wlih.app.dao;

import cn.wlih.app.model.Goods;
import cn.wlih.core.base.mapper.MyBaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GoodsMapper extends MyBaseMapper<Goods> {
}
