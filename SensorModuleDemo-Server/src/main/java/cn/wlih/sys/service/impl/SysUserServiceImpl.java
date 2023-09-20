package cn.wlih.sys.service.impl;

import cn.wlih.sys.mapper.SysUserMapper;
import cn.wlih.sys.model.SysUser;
import cn.wlih.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * 查询所有
     *
     * @return
     */
    @Override
    public List<SysUser> selectAll() {
        return sysUserMapper.selectList(null);
    }
}
