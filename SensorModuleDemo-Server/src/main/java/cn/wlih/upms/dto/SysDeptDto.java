package cn.wlih.upms.dto;

import cn.wlih.core.base.dto.BaseModelDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Schema(title = "系统部门表")
@EqualsAndHashCode(callSuper = true)
public class SysDeptDto extends BaseModelDto {

    @Schema(title = "所属组织ID（SysOrganization表主键ID）")
    private Long organizationId;

    @Schema(title = "部门负责人ID（SysUser表主键ID）")
    private Long userId;

    @Schema(title = "部门编码")
    private String deptCode;

    @Schema(title = "部门名称")
    private String deptName;

    @Schema(title = "部门电话号码")
    private String deptPhoneNumber;

    @Schema(title = "部门地址")
    private String deptAddress;

    @Schema(title = "部门简介")
    private String deptIntroduction;

}
