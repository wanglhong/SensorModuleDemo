package cn.wlih.sys.model;

import cn.wlih.core.base.BaseModel;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 *
 * @author 王立宏
 * 描述: 系统用户表
 * path: SensorModuleDemo-cn.wlih.sys.model-SysUser
 * date: 2023/9/20 17:15
 */
@Data
@TableName("sys_user")
public class SysUser extends BaseModel {

    @TableId
    private Long id;
    private String loginName;
    private String password;
    private String phoneNumber;

}
