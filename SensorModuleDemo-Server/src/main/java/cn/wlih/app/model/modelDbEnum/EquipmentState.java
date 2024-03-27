package cn.wlih.app.model.modelDbEnum;

import cn.wlih.core.myAnnotate.ClassComment;
import cn.wlih.core.myAnnotate.VariableComment;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@ClassComment("设备状态（1：闲置中、2：使用中、3：废弃、4：维修中）")
public enum EquipmentState {

    @VariableComment("闲置中")
    IDLE(1, "闲置中"),

    @VariableComment("使用中")
    IN_USE(2, "使用中"),

    @VariableComment("废弃")
    DISCARDED(3, "废弃"),

    @VariableComment("维修中")
    REPAIRING(4, "维修中");

    @EnumValue
    private final Integer key;

    @JsonValue
    private final String display;

}
