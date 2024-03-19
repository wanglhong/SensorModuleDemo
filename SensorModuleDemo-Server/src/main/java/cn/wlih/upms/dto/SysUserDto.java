package cn.wlih.upms.dto;

import cn.wlih.core.base.dto.BaseModelDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 描述: 系统用户表
 * @author 王立宏
 * @date 2023/9/20 17:15
 * @path SensorModuleDemo-cn.wlih.sys.model-SysUser
 */
@Data
@Schema(defaultValue = "系统用户表")
@EqualsAndHashCode(callSuper = true)
public class SysUserDto extends BaseModelDto {

    @Schema(defaultValue = "所属部门ID（SysDept表主键ID）")
    private Long deptId;

    @Schema(defaultValue = "用户名称")
    private String userName;

    @Schema(defaultValue = "登录名")
    private String loginName;

    @Schema(defaultValue = "密码")
    private String password;

    @Schema(defaultValue = "电话号码")
    private String phoneNumber;

    @Schema(defaultValue = "邮箱")
    private String email;

}
