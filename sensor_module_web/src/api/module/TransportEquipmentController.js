import Http from "@/api/http.js"

export const add = function(removeObject) {
    return Http.post("/api/app/transportEquipment/add", removeObject)
}

export const list = function(queryCriteria) {
    return Http.post("/api/app/transportEquipment/list", queryCriteria)
}

export const update = function(updateObject) {
    return Http.post("/api/app/transportEquipment/updateById", updateObject)
}

export const remove = function(removeObject) {
    return Http.post("/api/app/transportEquipment/delete", removeObject)
}

export const removeByIdList = function(removeObject) {
    return Http.post("/api/app/transportEquipment/removeByIdList", removeObject)
}
