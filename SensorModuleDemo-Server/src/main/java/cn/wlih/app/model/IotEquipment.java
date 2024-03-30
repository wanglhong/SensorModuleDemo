package cn.wlih.app.model;

import cn.wlih.app.model.modelDbEnum.EquipmentState;
import cn.wlih.core.base.model.BaseModel;
import cn.wlih.core.myAnnotate.ClassComment;
import cn.wlih.core.myAnnotate.VariableComment;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("sm_iot_equipment")
@ClassComment("物联网设备信息表")
@EqualsAndHashCode(callSuper = true)
public class IotEquipment extends BaseModel {

    @VariableComment("运输设备ID")
    private Long transportEquipmentId;

    @VariableComment("设备名称")
    private String iotName;

    @VariableComment("设备编号")
    private String iotCode;

    @VariableComment("设备key")
    private String iotKey;

    @VariableComment("设备状态（1：闲置中、2：使用中、3：废弃、4：维修中）")
    private EquipmentState iotState;

    @VariableComment("备注")
    private String iotRemark;
}
