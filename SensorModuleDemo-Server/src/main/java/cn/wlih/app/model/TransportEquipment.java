package cn.wlih.app.model;

import cn.wlih.app.model.modelDbEnum.EquipmentState;
import cn.wlih.app.model.modelDbEnum.TransportEquipmentType;
import cn.wlih.core.base.model.BaseModel;
import cn.wlih.core.myAnnotate.ClassComment;
import cn.wlih.core.myAnnotate.VariableComment;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("sm_transport_equipment")
@ClassComment("运输设备信息表")
@EqualsAndHashCode(callSuper = true)
public class TransportEquipment extends BaseModel {

    @VariableComment("设备编码")
    private String equipmentCode;

    @VariableComment("设备名称")
    private String equipmentName;

    @VariableComment("设备类型")
    private TransportEquipmentType equipmentType;

    @VariableComment("设备型号")
    private String equipmentModel;

    @VariableComment("设备品牌")
    private String equipmentBrand;

    @VariableComment("设备状态")
    private EquipmentState equipmentState;

    @VariableComment("备注")
    private String equipmentRemark;

}
