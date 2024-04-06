package cn.wlih.app.model;

import cn.wlih.core.base.model.BaseModel;
import cn.wlih.core.myAnnotate.ClassComment;
import cn.wlih.core.myAnnotate.VariableComment;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("sm_clearance_info_business_file")
@ClassComment("清关信息与附件多对多关联表")
@EqualsAndHashCode(callSuper = true)
public class ClearanceInfoBusinessFile extends BaseModel {

    @VariableComment("清关信息ID")
    private Long clearanceInfoId;

    @VariableComment("附件ID")
    private Long businessFileId;

    @VariableComment("备注")
    private String remark;

}
