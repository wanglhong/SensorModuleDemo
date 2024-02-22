package cn.wlih.core.base.model;

import cn.wlih.core.myAnnotate.DbBaseField;
import cn.wlih.core.myEnum.DbBaseFieldType;
import cn.wlih.core.myEnum.dbEnum.IsDeleteEnum;
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
    @DbBaseField(type = DbBaseFieldType.ID)
    private Long id;

    @VariableComment("创建时间")
    @DbBaseField(type = DbBaseFieldType.CREATE_TIME)
    private Date createTime;

    @VariableComment("创建者ID")
    @DbBaseField(type = DbBaseFieldType.CREATE_USER_ID)
    private Long createUserId;

    @VariableComment("修改时间")
    @DbBaseField(type = DbBaseFieldType.UPDATE_TIME)
    private Date updateTime;

    @VariableComment("修改者ID")
    @DbBaseField(type = DbBaseFieldType.UPDATE_USER_ID)
    private Long updateUserId;

    @VariableComment("数据所属人ID")
    @DbBaseField(type = DbBaseFieldType.DATA_USER_ID)
    private Long dataUserId;

    @VariableComment("数据所属部门ID")
    @DbBaseField(type = DbBaseFieldType.DATA_DEPT_ID)
    private Long dataDeptId;

    @VariableComment("逻辑删除（1：存在。-1：逻辑删除）")
    @DbBaseField(type = DbBaseFieldType.IS_DELETE)
    private IsDeleteEnum isDelete;

}
