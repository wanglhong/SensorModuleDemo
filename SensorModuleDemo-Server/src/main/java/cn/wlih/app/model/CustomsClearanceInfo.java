package cn.wlih.app.model;

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

    @VariableComment("清关信息名称")
    private String customsClearanceName;

    @VariableComment("运输信息ID")
    private Long transportInfoId;

    @VariableComment("报关信息ID")
    private Long customsDeclarationInfoId;

    @VariableComment("清关单号")
    private String customsClearanceNumber;

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
