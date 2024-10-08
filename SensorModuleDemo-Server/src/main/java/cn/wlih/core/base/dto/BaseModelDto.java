package cn.wlih.core.base.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(title = "基础模型")
public abstract class BaseModelDto {

    @Schema(title = "主键ID")
    private Long id;

//    @Schema(title = "创建时间")
//    private Date createTime;
//
//    @Schema(title = "创建者ID")
//    private Long createUserId;
//
//    @Schema(title = "修改时间")
//    private Date updateTime;
//
//    @Schema(title = "修改者ID")
//    private Long updateUserId;
//
//    @Schema(title = "数据所属人ID")
//    private Long dataUserId;
//
//    @Schema(title = "数据所属部门ID")
//    private Long dataDeptId;

}
