package cn.wlih.sys.service;

import cn.wlih.sys.model.SysUser;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author 王立宏
 * 描述:
 * path: SensorModuleDemo-cn.wlih.sys.service-SysUserService
 * date: 2023/9/20 17:18
 */
public interface SysUserService {
    /**
     * 查询所有
     * @return
     */
    List<SysUser> selectAll();
}
