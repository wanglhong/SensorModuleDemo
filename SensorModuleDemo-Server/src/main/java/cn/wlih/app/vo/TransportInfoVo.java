package cn.wlih.app.vo;

import cn.wlih.core.base.vo.BaseModelVo;
import cn.wlih.core.myAnnotate.VariableComment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Schema(defaultValue = "运输信息表")
@EqualsAndHashCode(callSuper = true)
public class TransportInfoVo extends BaseModelVo {

    @VariableComment("运输人ID")
    private Long userId;

    @VariableComment("运输工具ID")
    private Long transportEquipmentId;

    @VariableComment("运输方式")
    private String transportMode;

    @VariableComment("发货公司ID")
    private Long sendOrganizationId;

    @VariableComment("收货公司ID")
    private Long receiveOrganizationId;

    @VariableComment("起运国")
    private String sendCountry;

    @VariableComment("目的国")
    private String receiveCountry;

}
