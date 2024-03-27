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

/**
 * 运输设备DTO
 */
export function transportEquipmentDto() {
    return reactive({
        // 运输设备id
        id: null,
        // 设备编码
        equipmentCode: null,
        // 设备名称
        equipmentName: null,
        // 运输设备类型（1：飞机、2：火车、3：卡车、4：轮船）
        equipmentType: null,
        // 设备型号
        equipmentModel: null,
        // 设备品牌
        equipmentBrand: null,
        // 设备状态（1：闲置中、2：使用中、3：废弃、4：维修中）
        equipmentState: null,
        // 设备备注
        equipmentRemark: null
    })
}
