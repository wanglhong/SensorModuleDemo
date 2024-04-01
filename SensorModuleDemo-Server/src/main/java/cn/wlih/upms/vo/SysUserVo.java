package cn.wlih.upms.vo;

import cn.wlih.core.base.vo.BaseModelVo;
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
@Schema(title = "系统用户表")
@EqualsAndHashCode(callSuper = true)
public class SysUserVo extends BaseModelVo {

    @Schema(title = "所属部门ID（SysDept表主键ID）")
    private Long deptId;

    @Schema(title = "用户名称")
    private String userName;

    @Schema(title = "登录名")
    private String loginName;

    @Schema(title = "电话号码")
    private String phoneNumber;

    @Schema(title = "邮箱")
    private String email;

}
