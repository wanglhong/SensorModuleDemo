import {reactive} from "vue";

/**
 * 货物DTO
 * @returns {reactive({goodsUnitWeight: null, id: null, goodsCode: null, goodsName: null, goodsUnitValue: null, goodsUnitVolume: null, goodsRemark: null})}
 */
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
        goodsRemark: null
    })
}

/**
 * 周转箱DTO
 */
export function turnoverBoxDto() {
    return reactive({
        // 周转箱id
        id: null,
        // RFID编码
        rfidCode: null,
        // 周转箱名称
        turnoverBoxName: null,
        // 周转箱体积（单位：cm³）
        turnoverBoxVolume: null,
        // 周转箱容积（单位：cm³）
        turnoverBoxContainer: null,
        // 周转箱重量（单位：克）
        turnoverBoxWeight: null,
        // 周转箱状态（1：闲置中、2：使用中、3：废弃）
        turnoverBoxState: null,
        // 周转箱备注
        turnoverBoxRemark: null
    })
}
