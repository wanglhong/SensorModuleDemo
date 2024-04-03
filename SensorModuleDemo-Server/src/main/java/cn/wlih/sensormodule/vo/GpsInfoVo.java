package cn.wlih.sensormodule.vo;

import cn.wlih.core.base.vo.BaseModelVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@Schema(title = "GPS定位信息表")
@EqualsAndHashCode(callSuper = true)
public class GpsInfoVo extends BaseModelVo {

    @Schema(title = "运输信息ID")
    private Long transportInfoId;

    /**
     * DECIMAL(10, 7) 总共10位数字，其中7位是小数部分
     */
    @Schema(title = "经度")
    private BigDecimal longitude;

    @Schema(title = "纬度")
    private BigDecimal latitude;

    @Schema(title = "速度")
    private String speed;

    @Schema(title = "方向")
    private String direction;

    @Schema(title = "海拔")
    private String altitude;

}
