package cn.wlih.sys.service;

import cn.wlih.sys.model.SysUser;

import java.util.List;

/**
 * 描述:
 * @author 王立宏
 * @date 2023/9/20 17:18
 * @path SensorModuleDemo-cn.wlih.sys.service-SysUserService
 */
public interface SysUserService {

    /**
     * 新增
     *
     * @param sysUser 需要保存的用户信息
     * @return 保存的用户信息
     * @author 王立宏
     * @date 2023/10/08 09:34
     */
    SysUser saveNew(SysUser sysUser);

    /**
     * 查询所有
     * @return
     */
    List<SysUser> selectAll();

}
