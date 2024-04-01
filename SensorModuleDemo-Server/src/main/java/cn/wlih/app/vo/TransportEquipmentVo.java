package cn.wlih.app.vo;

import cn.wlih.app.model.modelDbEnum.EquipmentState;
import cn.wlih.app.model.modelDbEnum.TransportEquipmentType;
import cn.wlih.core.base.vo.BaseModelVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Schema(title = "运输设备信息表")
@EqualsAndHashCode(callSuper = true)
public class TransportEquipmentVo extends BaseModelVo {

    @Schema(title = "设备编码")
    private String equipmentCode;

    @Schema(title = "设备名称")
    private String equipmentName;

    @Schema(title = "设备类型")
    private TransportEquipmentType equipmentType;

    @Schema(title = "设备型号")
    private String equipmentModel;

    @Schema(title = "设备品牌")
    private String equipmentBrand;

    @Schema(title = "设备状态")
    private EquipmentState equipmentState;

    @Schema(title = "备注")
    private String equipmentRemark;

}
