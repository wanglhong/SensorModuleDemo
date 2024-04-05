package cn.wlih.app.model;

import cn.wlih.app.vo.BusinessFileVo;
import cn.wlih.core.base.model.BaseModel;
import cn.wlih.core.myAnnotate.ClassComment;
import cn.wlih.core.myAnnotate.RelationOneToOne;
import cn.wlih.core.myAnnotate.VariableComment;
import cn.wlih.upms.model.SysOrganization;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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

    @VariableComment("检验检疫机构ID")
    private Long inspectionOrganizationId;

    @VariableComment("检验检疫结果")
    private String inspectionResult;

    @VariableComment("关税和税费")
    private BigDecimal taxAndFees;

    @VariableComment("放行日期")
    private Date clearanceDate;

    @VariableComment("备注")
    private String remark;

    @VariableComment("运输信息")
    @TableField(exist = false)
    @RelationOneToOne(
            masterIdField = "transportInfoId",
            slaveIdField = "id",
            slaveModelClass = TransportInfo.class,
            slaveServiceName = "transportInfoService"
    )
    private TransportInfo transportInfo;

    @VariableComment("报关信息")
    @TableField(exist = false)
    @RelationOneToOne(
            masterIdField = "customsDeclarationInfoId",
            slaveIdField = "id",
            slaveModelClass = CustomsDeclarationInfo.class,
            slaveServiceName = "customsDeclarationInfoService"
    )
    private CustomsDeclarationInfo customsDeclarationInfo;

    @VariableComment("检验检疫机构信息")
    @TableField(exist = false)
    @RelationOneToOne(
            masterIdField = "inspectionOrganizationId",
            slaveIdField = "id",
            slaveModelClass = SysOrganization.class,
            slaveServiceName = "sysOrganizationService"
    )
    private SysOrganization inspectionOrganization;

    @VariableComment("清关附件信息集合")
    @TableField(exist = false)
    private List<BusinessFileVo> businessFileList;

}
