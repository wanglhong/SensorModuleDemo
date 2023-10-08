package cn.wlih.core.base;

import cn.wlih.core.dbEnum.baseEnum.IsDeleteEnum;
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
public class BaseModel {

    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改者ID
     */
    private Long createUserId;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 修改者ID
     */
    private Long updateUserId;
    /**
     * 逻辑删除
     */
    private IsDeleteEnum isDelete;

}
