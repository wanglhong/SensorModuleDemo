package cn.wlih.app.vo;

import cn.wlih.app.model.modelDbEnum.EquipmentState;
import cn.wlih.app.model.modelDbEnum.TransportEquipmentType;
import cn.wlih.core.base.vo.BaseModelVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Schema(defaultValue = "运输设备信息表")
@EqualsAndHashCode(callSuper = true)
public class TransportEquipmentVo extends BaseModelVo {

    @Schema(defaultValue = "设备编码")
    private String equipmentCode;

    @Schema(defaultValue = "设备名称")
    private String equipmentName;

    @Schema(defaultValue = "设备类型")
    private TransportEquipmentType equipmentType;

    @Schema(defaultValue = "设备型号")
    private String equipmentModel;

    @Schema(defaultValue = "设备品牌")
    private String equipmentBrand;

    @Schema(defaultValue = "设备状态")
    private EquipmentState equipmentState;

    @Schema(defaultValue = "备注")
    private String equipmentRemark;

}
