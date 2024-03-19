package cn.wlih.app.model;

import cn.wlih.app.model.modelDbEnum.CustomsClearanceState;
import cn.wlih.core.base.model.BaseModel;
import cn.wlih.core.myAnnotate.ClassComment;
import cn.wlih.core.myAnnotate.VariableComment;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("sm_customs_clearance_info")
@ClassComment("海关清关信息表")
@EqualsAndHashCode(callSuper = true)
public class CustomsClearanceInfo extends BaseModel {

    @VariableComment("运输信息ID")
    private Long transportInfoId;

    @VariableComment("报关信息ID")
    private Long customsDeclarationInfoId;

    @VariableComment("清关单号")
    private String customsClearanceNumber;

    @VariableComment("清关状态（1：待审核、2：审核中、3：已放行）")
    private CustomsClearanceState customsClearanceState;

    @VariableComment("审批机构ID")
    private Long organizationId;

    @VariableComment("检验检疫结果")
    private String inspectionResult;

    @VariableComment("关税和税费")
    private BigDecimal taxAndFees;

    @VariableComment("放行日期")
    private Date clearanceDate;

    @VariableComment("备注")
    private String remark;

}
