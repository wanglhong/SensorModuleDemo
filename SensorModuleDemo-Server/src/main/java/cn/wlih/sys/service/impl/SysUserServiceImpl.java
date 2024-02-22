package cn.wlih.sys.service.impl;

import cn.wlih.core.base.service.impl.MyBaseServiceImpl;
import cn.wlih.core.dbEnum.baseEnum.IsDeleteEnum;
import cn.wlih.sys.mapper.SysUserMapper;
import cn.wlih.sys.model.SysUser;
import cn.wlih.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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

}
