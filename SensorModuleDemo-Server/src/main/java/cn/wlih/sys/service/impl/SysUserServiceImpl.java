package cn.wlih.sys.service.impl;

import cn.wlih.core.dbEnum.baseEnum.IsDeleteEnum;
import cn.wlih.sys.mapper.SysUserMapper;
import cn.wlih.sys.model.SysUser;
import cn.wlih.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author 王立宏
 * 描述:
 * path: SensorModuleDemo-cn.wlih.sys.service.impl-SysUserServiceImpl
 * date: 2023/9/20 17:19
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 新增
     *
     * @param sysUser 需要保存的用户信息
     * @return 保存的用户信息
     * @author 王立宏
     * @date 2023/10/08 09:34
     */
    @Override
    public SysUser saveNew(SysUser sysUser) {
        sysUser.setId(1001L);
        sysUser.setCreateUserId(1001L);
        sysUser.setCreateTime(new Date());
        sysUser.setUpdateUserId(1001L);
        sysUser.setUpdateTime(new Date());
        sysUser.setIsDelete(IsDeleteEnum.EXIST);
        return sysUserMapper.insert(sysUser) == 1 ? sysUser : null;
    }

    /**
     * 查询所有
     *
     * @return
     */
    @Override
    public List<SysUser> selectAll() {
        return sysUserMapper.selectList(null);
    }
}
