package cn.wlih.sys.service.impl;

import cn.wlih.core.base.mapper.MyBaseMapper;
import cn.wlih.core.base.service.impl.MyBaseServiceImpl;
import cn.wlih.sys.dao.SysDeptMapper;
import cn.wlih.sys.model.SysDept;
import cn.wlih.sys.service.SysDeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service("sysDeptService")
public class SysDeptServiceImpl extends MyBaseServiceImpl<SysDept> implements SysDeptService {

    @Autowired
    private SysDeptMapper sysDeptMapper;

    /**
     * @return 当前Service的主表Mapper对象
     */
    @Override
    protected MyBaseMapper<SysDept> mapper() {
        return sysDeptMapper;
    }

}
