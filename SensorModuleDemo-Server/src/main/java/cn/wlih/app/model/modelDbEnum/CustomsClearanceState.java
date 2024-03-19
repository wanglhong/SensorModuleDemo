package cn.wlih.app.model.modelDbEnum;

import cn.wlih.core.myAnnotate.ClassComment;
import cn.wlih.core.myAnnotate.VariableComment;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

@ClassComment("清关状态（1：待审核、2：审核中、3：已放行）")
public enum CustomsClearanceState {

    @VariableComment("待审核")
    WAIT_AUDIT(1, "待审核"),
    @VariableComment("审核中")
    AUDITING(2, "审核中"),
    @VariableComment("已放行")
    ALLOWED(3, "已放行");

    @EnumValue
    private Integer key;
    @JsonValue
    private String display;

    CustomsClearanceState(Integer key, String display) {
        this.key = key;
        this.display = display;
    }

    public Integer getKey() {
        return key;
    }
    public String getDisplay() {
        return display;
    }

}
