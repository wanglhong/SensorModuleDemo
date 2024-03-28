import Http from "@/api/http.js"

export const add = function(removeObject) {
    return Http.post("/api/app/transportInfo/add", removeObject)
}

export const list = function(queryCriteria) {
    return Http.post("/api/app/transportInfo/list", queryCriteria)
}

export const update = function(updateObject) {
    return Http.post("/api/app/transportInfo/updateById", updateObject)
}

export const remove = function(removeObject) {
    return Http.post("/api/app/transportInfo/delete", removeObject)
}

export const removeByIdList = function(removeObject) {
    return Http.post("/api/app/transportInfo/removeByIdList", removeObject)
}
