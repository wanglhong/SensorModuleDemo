package cn.wlih.upms.service.impl;

import cn.wlih.core.base.mapper.MyBaseMapper;
import cn.wlih.core.base.service.impl.MyBaseServiceImpl;
import cn.wlih.upms.dao.SysUserMapper;
import cn.wlih.upms.model.SysUser;
import cn.wlih.upms.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 描述:
 * @author 王立宏
 * @date 2023/9/20 17:19
 * @path SensorModuleDemo-cn.wlih.sys.service.impl-SysUserServiceImpl
 */
@Slf4j
@Service("sysUserService")
public class SysUserServiceImpl extends MyBaseServiceImpl<SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * @return 当前Service的主表Mapper对象
     */
    @Override
    protected MyBaseMapper<SysUser> mapper() {
        return sysUserMapper;
    }

}
