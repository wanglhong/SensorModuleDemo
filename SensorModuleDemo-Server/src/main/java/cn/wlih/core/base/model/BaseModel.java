package cn.wlih.core.base.model;

import cn.wlih.core.dbEnum.baseEnum.IsDeleteEnum;
import cn.wlih.core.myAnnotate.ClassComment;
import cn.wlih.core.myAnnotate.VariableComment;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * 描述: 基础类
 *
 * @author 王立宏
 * @date 2023/9/28 0:32
 * @path SensorModuleDemo-cn.wlih.core.base-BaseModel
 */
@Data
@ClassComment("基础模型")
public abstract class BaseModel {

    @TableId
    @VariableComment("主键ID")
    private Long id;
    @VariableComment("创建时间")
    private Date createTime;
    @VariableComment("创建者ID")
    private Long createUserId;
    @VariableComment("修改时间")
    private Date updateTime;
    @VariableComment("修改者ID")
    private Long updateUserId;
    @VariableComment("逻辑删除（1：存在。-1：逻辑删除）")
    private IsDeleteEnum isDelete;

}
