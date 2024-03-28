import Http from "@/api/http.js"

export const add = function(removeObject) {
    return Http.post("/api/app/goods/add", removeObject)
}

export const list = function(queryCriteria) {
    return Http.post("/api/app/goods/list", queryCriteria)
}

export const update = function(updateObject) {
    return Http.post("/api/app/goods/updateById", updateObject)
}

export const remove = function(removeObject) {
    return Http.post("/api/app/goods/delete", removeObject)
}

export const removeByIdList = function(removeObject) {
    return Http.post("/api/app/goods/removeByIdList", removeObject)
}
