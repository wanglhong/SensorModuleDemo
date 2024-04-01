package cn.wlih.app.model.modelDbEnum;

import cn.wlih.core.myAnnotate.ClassComment;
import cn.wlih.core.myAnnotate.VariableComment;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@ClassComment("审批类型（1：报关审批、2：清关审批）")
public enum AuditType {

    @VariableComment("报关审批")
    CUSTOMS_DECLARATION(1, "报关审批"),

    @VariableComment("清关审批")
    CUSTOMS_CLEARANCE(2, "清关审批");

    @EnumValue
    private final Integer key;

    @JsonValue
    private final String display;

}
