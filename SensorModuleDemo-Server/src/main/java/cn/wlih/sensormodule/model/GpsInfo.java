package cn.wlih.sensormodule.model;

import cn.wlih.core.base.model.BaseModel;
import cn.wlih.core.myAnnotate.ClassComment;
import cn.wlih.core.myAnnotate.VariableComment;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@TableName("sm_gps_info")
@ClassComment("GPS定位信息表")
@EqualsAndHashCode(callSuper = true)
public class GpsInfo extends BaseModel {

    @VariableComment("运输信息ID")
    private Long transportInfoId;

    @VariableComment("序号")
    private Integer serialNumber;

    /**
     * DECIMAL(10, 7) 总共10位数字，其中7位是小数部分
     */
    @VariableComment("经度")
    private BigDecimal longitude;

    @VariableComment("纬度")
    private BigDecimal latitude;

    @VariableComment("速度")
    private String speed;

    @VariableComment("方向")
    private String direction;

    @VariableComment("海拔")
    private String altitude;

}
