import Http from '@/api/http.js'

export const add = function (removeObject) {
  return Http.post('/api/upms/organization/add', removeObject)
}

export const list = function (queryCriteria) {
  return Http.post('/api/upms/organization/list', queryCriteria)
}

export const update = function (updateObject) {
  return Http.post('/api/upms/organization/updateById', updateObject)
}

export const remove = function (removeObject) {
  return Http.post('/api/upms/organization/delete', removeObject)
}

export const removeByIdList = function (removeObject) {
  return Http.post('/api/upms/organization/removeByIdList', removeObject)
}

export const getUserTree = function () {
  return Http.get('/api/upms/organization/getUserTree')
}

export const getEquipmentTree = function () {
  return Http.get('/api/upms/organization/getEquipmentTree')
}
