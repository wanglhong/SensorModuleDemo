package cn.wlih.app.model.modelDbEnum;

import cn.wlih.core.myAnnotate.ClassComment;
import cn.wlih.core.myAnnotate.VariableComment;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@ClassComment("运输状态（1：未开始、2：运输中、3：已结束）")
public enum TransportState {

    @VariableComment("未开始")
    NOT_START(1, "未开始"),

    @VariableComment("运输中")
    IN_TRANSIT(2, "运输中"),

    @VariableComment("已结束")
    END(3, "已结束");

    @EnumValue
    private final Integer key;

    @JsonValue
    private final String display;

}
