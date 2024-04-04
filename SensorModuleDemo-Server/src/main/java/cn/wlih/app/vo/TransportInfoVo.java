package cn.wlih.app.vo;

import cn.wlih.app.model.modelDbEnum.TransportState;
import cn.wlih.core.base.vo.BaseModelVo;
import cn.wlih.upms.vo.SysOrganizationVo;
import cn.wlih.upms.vo.SysUserVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@Schema(title = "运输信息表")
@EqualsAndHashCode(callSuper = true)
public class TransportInfoVo extends BaseModelVo {

    @Schema(title = "运输名称")
    private String transportInfoName;

    @Schema(title = "运输人ID")
    private Long userId;

    @Schema(title = "运输工具ID")
    private Long transportEquipmentId;

    @Schema(title = "运输方式")
    private String transportMode;

    @Schema(title = "发货公司ID")
    private Long sendOrganizationId;

    @Schema(title = "收货公司ID")
    private Long receiveOrganizationId;

    @Schema(title = "起运国")
    private String sendCountry;

    @Schema(title = "目的国")
    private String receiveCountry;

    @Schema(title = "起运时间")
    private Date sendDate;

    @Schema(title = "预计过境时间")
    private Date estimateDate;

    @Schema(title = "实际过境时间")
    private Date actualDate;

    @Schema(title = "运输状态")
    private TransportState transportState;

    @Schema(title = "备注")
    private String remark;

    @Schema(title = "运输人信息")
    private SysUserVo transportUser;

    @Schema(title = "运输工具信息")
    private TransportEquipmentVo transportEquipment;

    @Schema(title = "发货公司信息")
    private SysOrganizationVo sendOrganization;

    @Schema(title = "收货公司信息")
    private SysOrganizationVo receiveOrganization;

}
