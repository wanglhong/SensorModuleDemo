package cn.wlih.app.model.modelDbEnum;

import cn.wlih.core.myAnnotate.ClassComment;
import cn.wlih.core.myAnnotate.VariableComment;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@ClassComment("审核状态（1：待提交审核、2：审核中、3：审核通过、4：审核驳回）")
public enum AuditState {

    @VariableComment("待提交审核")
    WAIT_AUDIT(1, "待提交审核"),

    @VariableComment("审核中")
    AUDITING(2, "审核中"),

    @VariableComment("审核通过")
    AUDIT_PASS(3, "审核通过"),

    @VariableComment("审核驳回")
    AUDIT_REJECT(4, "审核驳回");

    @EnumValue
    private final Integer key;

    @JsonValue
    private final String display;

}
