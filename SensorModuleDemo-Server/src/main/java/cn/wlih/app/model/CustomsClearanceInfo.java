package cn.wlih.app.model;

import cn.wlih.core.base.model.BaseModel;
import cn.wlih.core.myAnnotate.ClassComment;
import cn.wlih.core.myAnnotate.VariableComment;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("customs_clearance_info")
@ClassComment("海关清关信息表")
@EqualsAndHashCode(callSuper = true)
public class CustomsClearanceInfo extends BaseModel {

    @VariableComment("运输信息ID")
    private Long transportInfoId;

    @VariableComment("报关信息ID")
    private Long customsDeclarationInfoId;

    @VariableComment("清关单号")
    private String customsClearanceNumber;

}
