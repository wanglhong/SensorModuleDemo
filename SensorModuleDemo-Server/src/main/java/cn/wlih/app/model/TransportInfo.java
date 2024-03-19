package cn.wlih.app.model;

import cn.wlih.core.base.model.BaseModel;
import cn.wlih.core.myAnnotate.ClassComment;
import cn.wlih.core.myAnnotate.VariableComment;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("transport_info")
@ClassComment("运输信息表")
@EqualsAndHashCode(callSuper = true)
public class TransportInfo extends BaseModel {

    @VariableComment("运输人ID")
    private Long userId;

    @VariableComment("运输工具ID")
    private Long transportEquipmentId;

    @VariableComment("运输方式")
    private String transportMode;

    @VariableComment("发货公司ID")
    private Long sendOrganizationId;

    @VariableComment("收货公司ID")
    private Long receiveOrganizationId;

    @VariableComment("起运国")
    private String sendCountry;

    @VariableComment("目的国")
    private String receiveCountry;

}
