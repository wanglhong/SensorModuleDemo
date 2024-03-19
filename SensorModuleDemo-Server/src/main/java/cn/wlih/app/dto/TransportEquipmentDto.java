package cn.wlih.app.dto;

import cn.wlih.core.base.dto.BaseModelDto;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("transport_equipment")
@Schema(defaultValue = "运输设备信息表")
@EqualsAndHashCode(callSuper = true)
public class TransportEquipmentDto extends BaseModelDto {
}
