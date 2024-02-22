package cn.wlih.core.base.service.impl;

import cn.wlih.core.base.service.MyBaseService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class MyBaseServiceImpl<M> implements MyBaseService<M> {

    /**
     * 新增数据
     *
     * @param m 需要新增的数据
     * @return 成功返回true，失败返回false
     */
    @Override
    public Boolean add(M m) {
        log.info("BaseService新增数据");
        return true;
    }

}
