package cn.wlih.app.model;

import cn.wlih.app.vo.BusinessFileVo;
import cn.wlih.core.base.model.BaseModel;
import cn.wlih.core.myAnnotate.ClassComment;
import cn.wlih.core.myAnnotate.RelationOneToOne;
import cn.wlih.core.myAnnotate.VariableComment;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@TableName("sm_customs_declaration_info")
@ClassComment("海关报关信息表")
@EqualsAndHashCode(callSuper = true)
public class CustomsDeclarationInfo extends BaseModel {

    @VariableComment("报关信息名称")
    private String customsDeclarationName;

    @VariableComment("运输信息ID")
    private Long transportInfoId;

    @VariableComment("报关单号")
    private String customsDeclarationNumber;

    @VariableComment("HS编码")
    private String hsCode;

    @VariableComment("货物价值（用于计算关税和税费）")
    private BigDecimal goodsValue;

    @VariableComment("预计过境日期")
    private Date estimatedTransitDate;

    @VariableComment("实际过境日期")
    private Date actualTransitDate;

    @VariableComment("运输信息")
    @TableField(exist = false)
    @RelationOneToOne(
            masterIdField = "transportInfoId",
            slaveIdField = "id",
            slaveModelClass = TransportInfo.class,
            slaveServiceName = "transportInfoService"
    )
    private TransportInfo transportInfo;

    @VariableComment("清关附件信息集合")
    @TableField(exist = false)
    private List<BusinessFileVo> businessFileList;

}
