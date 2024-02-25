package cn.wlih.sys.service.impl;

import cn.wlih.core.base.mapper.MyBaseMapper;
import cn.wlih.core.base.service.impl.MyBaseServiceImpl;
import cn.wlih.sys.mapper.SysUserMapper;
import cn.wlih.sys.model.SysUser;
import cn.wlih.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 描述:
 * @author 王立宏
 * @date 2023/9/20 17:19
 * @path SensorModuleDemo-cn.wlih.sys.service.impl-SysUserServiceImpl
 */
@Service("sysUserService")
public class SysUserServiceImpl extends MyBaseServiceImpl<SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 返回当前Service的主表Mapper对象
     * @return
     */
    @Override
    protected MyBaseMapper<SysUser> mapper() {
        return sysUserMapper;
    }

}
