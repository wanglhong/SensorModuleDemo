package cn.wlih.app.vo;

import cn.wlih.core.base.vo.BaseModelVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Schema(defaultValue = "运输设备信息表")
@EqualsAndHashCode(callSuper = true)
public class TransportEquipmentVo extends BaseModelVo {
}
