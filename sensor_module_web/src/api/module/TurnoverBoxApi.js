import Http from "@/api/http.js"

export const add = function(removeObject) {
    return Http.post("/api/app/turnoverBox/add", removeObject)
}

export const list = function(queryCriteria) {
    return Http.post("/api/app/turnoverBox/list", queryCriteria)
}

export const update = function(updateObject) {
    return Http.post("/api/app/turnoverBox/updateById", updateObject)
}

export const remove = function(removeObject) {
    return Http.post("/api/app/turnoverBox/delete", removeObject)
}

export const removeByIdList = function(removeObject) {
    return Http.post("/api/app/turnoverBox/removeByIdList", removeObject)
}
