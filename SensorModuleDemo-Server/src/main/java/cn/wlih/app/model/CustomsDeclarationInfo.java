package cn.wlih.app.model;

import cn.wlih.app.model.modelDbEnum.CustomsDeclarationState;
import cn.wlih.core.base.model.BaseModel;
import cn.wlih.core.myAnnotate.ClassComment;
import cn.wlih.core.myAnnotate.VariableComment;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("customs_declaration_info")
@ClassComment("海关报关信息表")
@EqualsAndHashCode(callSuper = true)
public class CustomsDeclarationInfo extends BaseModel {

    @VariableComment("运输信息ID")
    private Long transportInfoId;

    @VariableComment("报关单号")
    private String customsDeclarationNumber;

    @VariableComment("HS编码")
    private String hsCode;

    @VariableComment("货物价值（用于计算关税和税费）")
    private BigDecimal goodsValue;

    @VariableComment("报关状态")
    private CustomsDeclarationState customsDeclarationState;

    @VariableComment("预计过境日期")
    private Date estimatedTransitDate;

    @VariableComment("实际过境日期")
    private Date actualTransitDate;

}
