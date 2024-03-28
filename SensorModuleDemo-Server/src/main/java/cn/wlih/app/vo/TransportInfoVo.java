package cn.wlih.app.vo;

import cn.wlih.core.base.vo.BaseModelVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Schema(defaultValue = "运输信息表")
@EqualsAndHashCode(callSuper = true)
public class TransportInfoVo extends BaseModelVo {

    @Schema(defaultValue = "运输人ID")
    private Long userId;

    @Schema(defaultValue = "运输工具ID")
    private Long transportEquipmentId;

    @Schema(defaultValue = "运输方式")
    private String transportMode;

    @Schema(defaultValue = "发货公司ID")
    private Long sendOrganizationId;

    @Schema(defaultValue = "收货公司ID")
    private Long receiveOrganizationId;

    @Schema(defaultValue = "起运国")
    private String sendCountry;

    @Schema(defaultValue = "目的国")
    private String receiveCountry;

}
