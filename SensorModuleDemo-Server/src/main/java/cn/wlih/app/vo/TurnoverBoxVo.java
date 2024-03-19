package cn.wlih.app.vo;

import cn.wlih.core.base.vo.BaseModelVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Schema(defaultValue = "周转箱信息表")
@EqualsAndHashCode(callSuper = true)
public class TurnoverBoxVo extends BaseModelVo {
}
