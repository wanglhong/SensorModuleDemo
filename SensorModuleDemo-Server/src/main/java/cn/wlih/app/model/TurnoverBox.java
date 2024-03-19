package cn.wlih.app.model;

import cn.wlih.core.base.model.BaseModel;
import cn.wlih.core.myAnnotate.ClassComment;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("sm_turnover_box")
@ClassComment("周转箱信息表")
@EqualsAndHashCode(callSuper = true)
public class TurnoverBox extends BaseModel {
}
