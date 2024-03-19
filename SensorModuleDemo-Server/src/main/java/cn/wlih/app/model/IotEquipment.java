package cn.wlih.app.model;

import cn.wlih.core.base.model.BaseModel;
import cn.wlih.core.myAnnotate.ClassComment;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("turnover_box")
@ClassComment("物联网设备信息表")
@EqualsAndHashCode(callSuper = true)
public class IotEquipment extends BaseModel {
}
