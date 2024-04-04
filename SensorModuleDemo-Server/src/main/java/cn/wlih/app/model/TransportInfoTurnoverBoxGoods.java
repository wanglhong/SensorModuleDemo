package cn.wlih.app.model;

import cn.wlih.core.base.model.BaseModel;
import cn.wlih.core.myAnnotate.ClassComment;
import cn.wlih.core.myAnnotate.RelationOneToOne;
import cn.wlih.core.myAnnotate.VariableComment;
import cn.wlih.upms.model.SysUser;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("sm_transport_info_turnover_box_goods")
@ClassComment("运输信息-周转箱-货物关联表（1*N*M）")
@EqualsAndHashCode(callSuper = true)
public class TransportInfoTurnoverBoxGoods extends BaseModel {

    @VariableComment("运输信息ID")
    private Long transportInfoId;

    @VariableComment("周转箱ID")
    private Long turnoverBoxId;

    @VariableComment("货物ID")
    private Long goodsId;

    @VariableComment("货物数量")
    private Integer goodsNum;

    @VariableComment("货物装箱操作员ID")
    private Long goodsToBoxUserId;

    @VariableComment("周转箱装车操作员ID")
    private Long boxToTransportEquipmentUserId;

    @VariableComment("备注")
    private String remark;

    @TableField(exist = false)
    @RelationOneToOne(
            masterIdField = "goodsToBoxUserId",
            slaveIdField = "id",
            slaveModelClass = SysUser.class,
            slaveServiceName = "sysUserService"
    )
    @VariableComment("货物装箱操作员信息")
    private SysUser goodsToBoxUser;

    @TableField(exist = false)
    @RelationOneToOne(
            masterIdField = "boxToTransportEquipmentUserId",
            slaveIdField = "id",
            slaveModelClass = SysUser.class,
            slaveServiceName = "sysUserService"
    )
    @VariableComment("周转箱装车操作员信息")
    private SysUser boxToTransportEquipmentUser;

}
