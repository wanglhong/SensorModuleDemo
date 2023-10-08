package cn.wlih.core.dbEnum.baseEnum;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 描述:
 *
 * @author：王立宏
 * @date：2023/10/7 9:46
 * @path：SensorModuleDemo-cn.wlih.core.dbEnum.baseEnum-IsDeleteEnum
 */
public enum IsDeleteEnum {
    /**
     * 存在状态
     */
    EXIST(1, "存在"),
    /**
     * 删除状态
     */
    DELETE(-1, "删除");

    /**
     * 数据库存储值
     */
    @EnumValue
    private Integer key;
    /**
     * 前端显示值
     */
    @JsonValue
    private String display;

    IsDeleteEnum(Integer key, String display) {
        this.key = key;
        this.display = display;
    }

    public Integer getKey() {
        return key;
    }
    public String getDisplay() {
        return display;
    }
}
