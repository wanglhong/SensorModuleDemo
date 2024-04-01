package cn.wlih.app.model;

import cn.wlih.app.model.modelDbEnum.TransportState;
import cn.wlih.core.base.model.BaseModel;
import cn.wlih.core.myAnnotate.ClassComment;
import cn.wlih.core.myAnnotate.VariableComment;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@TableName("sm_transport_info")
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

    @VariableComment("起运时间")
    private Date sendDate;

    @VariableComment("预计过境时间")
    private Date estimateDate;

    @VariableComment("实际过境时间")
    private Date actualDate;

    @VariableComment("运输状态")
    private TransportState transportState;

    @VariableComment("备注")
    private String remark;

}
