package cn.wlih.upms.model;

import cn.wlih.core.base.model.BaseModel;
import cn.wlih.core.myAnnotate.ClassComment;
import cn.wlih.core.myAnnotate.VariableComment;
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
@TableName("sys_user")
@ClassComment("系统用户表")
@EqualsAndHashCode(callSuper = true)
public class SysUser extends BaseModel {

    @VariableComment("所属部门ID（SysDept表主键ID）")
    private Long deptId;

    @VariableComment("用户名称")
    private String userName;

    @VariableComment("登录名")
    private String loginName;

    @VariableComment("密码")
    private String password;

    @VariableComment("电话号码")
    private String phoneNumber;

    @VariableComment("邮箱")
    private String email;

}
