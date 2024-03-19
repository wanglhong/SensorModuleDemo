package cn.wlih.upms.model;

import cn.wlih.core.base.model.BaseModel;
import cn.wlih.core.myAnnotate.ClassComment;
import cn.wlih.core.myAnnotate.VariableComment;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("sys_dept")
@ClassComment("系统部门表")
@EqualsAndHashCode(callSuper = true)
public class SysDept extends BaseModel {

    @VariableComment("所属组织ID（SysOrganization表主键ID）")
    private String organizationId;

    @VariableComment("部门负责人ID（SysUser表主键ID）")
    private String userId;

    @VariableComment("部门编码")
    private String deptCode;

    @VariableComment("部门名称")
    private String deptName;

    @VariableComment("部门电话号码")
    private String deptPhoneNumber;

    @VariableComment("部门地址")
    private String deptAddress;

    @VariableComment("部门简介")
    private String deptIntroduction;

}
