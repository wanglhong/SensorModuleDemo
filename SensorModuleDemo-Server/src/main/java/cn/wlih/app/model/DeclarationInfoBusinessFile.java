package cn.wlih.app.model;

import cn.wlih.core.base.model.BaseModel;
import cn.wlih.core.myAnnotate.ClassComment;
import cn.wlih.core.myAnnotate.VariableComment;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("declaration_info_business_file")
@ClassComment("报关信息与附件多对多关联表")
@EqualsAndHashCode(callSuper = true)
public class DeclarationInfoBusinessFile extends BaseModel {

    @VariableComment("清关信息ID")
    private Long declarationInfoId;

    @VariableComment("附件ID")
    private Long businessFileId;

}
