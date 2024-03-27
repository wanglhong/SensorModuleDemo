package cn.wlih.app.model.modelDbEnum;

import cn.wlih.core.myAnnotate.ClassComment;
import cn.wlih.core.myAnnotate.VariableComment;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

@ClassComment("周转箱状态（1：闲置中、2：使用中、3：废弃）")
public enum TurnoverBoxState {

    @VariableComment("闲置中")
    IDLE(1, "闲置中"),

    @VariableComment("使用中")
    IN_USE(2, "使用中"),

    @VariableComment("废弃")
    DISCARDED(3, "废弃");

    TurnoverBoxState(Integer key, String display) {
        this.key = key;
        this.display = display;
    }

    @EnumValue
    private final Integer key;

    @JsonValue
    private final String display;

    public Integer getKey() {
        return key;
    }

    public String getDisplay() {
        return display;
    }
}
