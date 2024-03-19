package cn.wlih.app.dto;

import cn.wlih.core.base.dto.BaseModelDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Schema(defaultValue = "物联网设备信息表")
@EqualsAndHashCode(callSuper = true)
public class IotEquipmentDto extends BaseModelDto {
}
