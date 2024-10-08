package cn.wlih.app.vo;

import cn.wlih.app.model.modelDbEnum.EquipmentState;
import cn.wlih.core.base.vo.BaseModelVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Schema(title = "物联网设备信息表")
@EqualsAndHashCode(callSuper = true)
public class IotEquipmentVo extends BaseModelVo {

    @Schema(title = "运输设备ID")
    private Long transportEquipmentId;

    @Schema(title = "设备名称")
    private String iotName;

    @Schema(title = "设备编号")
    private String iotCode;

    @Schema(title = "设备key")
    private String iotKey;

    @Schema(title = "设备状态（1：闲置中、2：使用中、3：废弃、4：维修中）")
    private EquipmentState iotState;

    @Schema(title = "备注")
    private String iotRemark;

}
