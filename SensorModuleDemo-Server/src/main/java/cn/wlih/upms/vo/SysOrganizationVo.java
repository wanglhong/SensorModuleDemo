package cn.wlih.upms.vo;

import cn.wlih.core.base.vo.BaseModelVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@Schema(defaultValue = "系统组织（公司）表")
@EqualsAndHashCode(callSuper = true)
public class SysOrganizationVo extends BaseModelVo {

    @Schema(defaultValue = "法定代表人ID（SysUser表主键ID）")
    private Long userId;

    @Schema(defaultValue = "组织名称")
    private String organizationName;

    @Schema(defaultValue = "组织成立时间")
    private Date organizationEstablishmentTime;

    @Schema(defaultValue = "组织编码（统一社会信用代码）")
    private String organizationCode;

    @Schema(defaultValue = "组织电话号码")
    private String organizationPhoneNumber;

    @Schema(defaultValue = "组织邮箱")
    private String organizationEmail;

    @Schema(defaultValue = "组织官网")
    private String organizationWebsite;

    @Schema(defaultValue = "组织地址")
    private String organizationAddress;

    @Schema(defaultValue = "组织简介")
    private String organizationIntroduction;

}
