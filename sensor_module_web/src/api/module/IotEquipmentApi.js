import Http from "@/api/http.js"

export const add = function(removeObject) {
    return Http.post("/api/app/iotEquipment/add", removeObject)
}

export const list = function(queryCriteria) {
    return Http.post("/api/app/iotEquipment/list", queryCriteria)
}

export const update = function(updateObject) {
    return Http.post("/api/app/iotEquipment/updateById", updateObject)
}

export const remove = function(removeObject) {
    return Http.post("/api/app/iotEquipment/delete", removeObject)
}

export const removeByIdList = function(removeObject) {
    return Http.post("/api/app/iotEquipment/removeByIdList", removeObject)
}
