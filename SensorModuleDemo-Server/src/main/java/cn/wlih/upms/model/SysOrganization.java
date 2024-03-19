package cn.wlih.upms.model;

import cn.wlih.core.base.model.BaseModel;
import cn.wlih.core.myAnnotate.ClassComment;
import cn.wlih.core.myAnnotate.VariableComment;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@TableName("sys_organization")
@ClassComment("系统组织（公司）表")
@EqualsAndHashCode(callSuper = true)
public class SysOrganization extends BaseModel {

    @VariableComment("法定代表人ID（SysUser表主键ID）")
    private Long userId;

    @VariableComment("组织名称")
    private String organizationName;

    @VariableComment("组织成立时间")
    private Date organizationEstablishmentTime;

    @VariableComment("组织编码（统一社会信用代码）")
    private String organizationCode;

    @VariableComment("组织电话号码")
    private String organizationPhoneNumber;

    @VariableComment("组织邮箱")
    private String organizationEmail;

    @VariableComment("组织官网")
    private String organizationWebsite;

    @VariableComment("组织地址")
    private String organizationAddress;

    @VariableComment("组织简介")
    private String organizationIntroduction;

}
