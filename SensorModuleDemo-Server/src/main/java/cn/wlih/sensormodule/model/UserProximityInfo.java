package cn.wlih.sensormodule.model;

import cn.wlih.core.base.model.BaseModel;
import cn.wlih.core.myAnnotate.ClassComment;
import cn.wlih.core.myAnnotate.VariableComment;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@TableName("sm_user_proximity_info")
@ClassComment("人物靠近信息表")
@EqualsAndHashCode(callSuper = true)
public class UserProximityInfo extends BaseModel {

    @VariableComment("运输信息ID")
    private Long transportInfoId;

    @VariableComment("靠近时间")
    private Date proximityDate;

    @VariableComment("GPS信息ID")
    private Long gpsInfoId;

    @VariableComment("监控信息ID")
    private Long monitorInfoId;

}
