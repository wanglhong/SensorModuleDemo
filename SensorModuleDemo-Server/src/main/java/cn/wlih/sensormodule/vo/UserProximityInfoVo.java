package cn.wlih.sensormodule.vo;

import cn.wlih.core.base.vo.BaseModelVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@Schema(title = "人物靠近信息表")
@EqualsAndHashCode(callSuper = true)
public class UserProximityInfoVo extends BaseModelVo {

    @Schema(title = "运输信息ID")
    private Long transportInfoId;

    @Schema(title = "靠近时间")
    private Date proximityDate;

    @Schema(title = "GPS信息ID")
    private Long gpsInfoId;

    @Schema(title = "监控信息ID")
    private Long monitorInfoId;

}
