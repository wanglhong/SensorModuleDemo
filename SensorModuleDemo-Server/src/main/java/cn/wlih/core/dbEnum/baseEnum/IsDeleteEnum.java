package cn.wlih.core.dbEnum.baseEnum;

import cn.wlih.core.myAnnotate.ClassComment;
import cn.wlih.core.myAnnotate.VariableComment;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 描述: 逻辑删除
 *
 * @author 王立宏
 * @date 2023/10/7 9:46
 * @path SensorModuleDemo-cn.wlih.core.dbEnum.baseEnum-IsDeleteEnum
 */
@ClassComment("逻辑删除")
public enum IsDeleteEnum {
    @VariableComment("存在状态")
    EXIST(1, "存在"),
    @VariableComment("删除状态")
    DELETE(-1, "删除");

    /**
     * 数据库存储值
     * <p>
     *  标记的枚举类属性的类型要和数据库字段的类型对应，否则在查询数据的时候无法转化为枚举类型，并显示为null。
     *  如果查询的时候，数据库字段的值匹配不到枚举，程序运行时并不会报错，而是显示为null；
     * </p>
     */
    @EnumValue
    private Integer key;
    /**
     * 前端显示值
     * <p>
     *  在保存的时候，前端需要传递@JsonValue标记的枚举类属性的值，即"存在/删除"；
     *  也可以传递枚举的索引值，0或"0"会转换成 EXIST。
     * </p>
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
