import Http from '@/api/http.js'

export const add = function (removeObject) {
  return Http.post('/api/app/customsAudit/add', removeObject)
}

export const list = function (queryCriteria) {
  return Http.post('/api/app/customsAudit/list', queryCriteria)
}

export const update = function (updateObject) {
  return Http.post('/api/app/customsAudit/updateById', updateObject)
}

export const remove = function (removeObject) {
  return Http.post('/api/app/customsAudit/delete', removeObject)
}

export const removeByIdList = function (removeObject) {
  return Http.post('/api/app/customsAudit/removeByIdList', removeObject)
}
