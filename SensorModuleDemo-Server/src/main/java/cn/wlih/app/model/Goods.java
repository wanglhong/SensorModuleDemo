package cn.wlih.app.model;

import cn.wlih.core.base.model.BaseModel;
import cn.wlih.core.myAnnotate.ClassComment;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("goods")
@ClassComment("货物信息表")
@EqualsAndHashCode(callSuper = true)
public class Goods extends BaseModel {
}
