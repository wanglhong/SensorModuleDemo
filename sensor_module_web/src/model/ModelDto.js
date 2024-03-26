import {reactive} from "vue";

export function goodsDto() {
    return reactive({
        // 货物id
        id: null,
        // 货物编码
        goodsCode: null,
        // 货物名称
        goodsName: null,
        // 单位货物价值（单位：分）
        goodsUnitValue: null,
        // 货物体积（单位：cm³）
        goodsUnitVolume: null,
        // 货物重量（单位：克）
        goodsUnitWeight: null,
        // 货物描述
        goodsDescription: null
    })
}
