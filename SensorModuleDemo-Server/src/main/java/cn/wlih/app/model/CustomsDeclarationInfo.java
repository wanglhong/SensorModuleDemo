package cn.wlih.app.model;

import cn.wlih.core.base.model.BaseModel;
import cn.wlih.core.myAnnotate.ClassComment;
import cn.wlih.core.myAnnotate.VariableComment;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("customs_declaration_info")
@ClassComment("海关报关信息表")
@EqualsAndHashCode(callSuper = true)
public class CustomsDeclarationInfo extends BaseModel {

    @VariableComment("运输信息ID")
    private Long transportInfoId;

    @VariableComment("报关单号")
    private String customsDeclarationNumber;

}
