package cn.wlih.app.model.modelDbEnum;

import cn.wlih.core.myAnnotate.ClassComment;
import cn.wlih.core.myAnnotate.VariableComment;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@ClassComment("运输设备类型（1：飞机、2：火车、3：卡车、4：轮船）")
public enum TransportEquipmentType {

    @VariableComment("飞机")
    PLANE(1, "飞机"),

    @VariableComment("火车")
    TRAIN(2, "火车"),

    @VariableComment("卡车")
    TRUCK(3, "卡车"),

    @VariableComment("轮船")
    SHIP(4, "轮船");

    @EnumValue
    private final Integer key;

    @JsonValue
    private final String display;

}
