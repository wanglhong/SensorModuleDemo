package cn.wlih.sys.model;

import cn.wlih.core.base.BaseModel;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 描述: 系统用户表
 * @author 王立宏
 * @date 2023/9/20 17:15
 * @path SensorModuleDemo-cn.wlih.sys.model-SysUser
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user")
public class SysUser extends BaseModel {

    @TableId
    private Long id;
    private String loginName;
    private String password;
    private String phoneNumber;

}
