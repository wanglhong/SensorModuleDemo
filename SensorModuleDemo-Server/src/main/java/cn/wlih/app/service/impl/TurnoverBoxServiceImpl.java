package cn.wlih.app.service.impl;

import cn.wlih.app.dao.TurnoverBoxMapper;
import cn.wlih.app.model.TurnoverBox;
import cn.wlih.app.service.TurnoverBoxService;
import cn.wlih.core.base.mapper.MyBaseMapper;
import cn.wlih.core.base.service.impl.MyBaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service("turnoverBoxService")
public class TurnoverBoxServiceImpl extends MyBaseServiceImpl<TurnoverBox> implements TurnoverBoxService {

    @Autowired
    private TurnoverBoxMapper turnoverBoxMapper;

    /**
     * @return 当前Service的主表Mapper对象
     */
    @Override
    protected MyBaseMapper<TurnoverBox> mapper() {
        return turnoverBoxMapper;
    }

}
