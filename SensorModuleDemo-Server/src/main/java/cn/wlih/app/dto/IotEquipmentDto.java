package cn.wlih.app.dto;

import cn.wlih.app.model.modelDbEnum.EquipmentState;
import cn.wlih.core.base.dto.BaseModelDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Schema(defaultValue = "物联网设备信息表")
@EqualsAndHashCode(callSuper = true)
public class IotEquipmentDto extends BaseModelDto {

    @Schema(defaultValue = "运输设备ID")
    private Long transportEquipmentId;

    @Schema(defaultValue = "设备名称")
    private String iotName;

    @Schema(defaultValue = "设备编号")
    private String iotCode;

    @Schema(defaultValue = "设备key")
    private String iotKey;

    @Schema(defaultValue = "设备状态（1：闲置中、2：使用中、3：废弃、4：维修中）")
    private EquipmentState iotState;

    @Schema(defaultValue = "备注")
    private String iotRemark;

}
