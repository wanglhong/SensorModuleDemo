import Http from '@/api/http.js'

export const add = function (removeObject) {
  return Http.post('/api/app/customsClearanceInfo/add', removeObject)
}

export const list = function (queryCriteria) {
  return Http.post('/api/app/customsClearanceInfo/list', queryCriteria)
}

export const info = function (transportInfo) {
  return Http.post(
    '/api/app/customsClearanceInfo/viewByTransportInfo',
    transportInfo
  )
}

export const update = function (updateObject) {
  return Http.post('/api/app/customsClearanceInfo/updateById', updateObject)
}

export const remove = function (removeObject) {
  return Http.post('/api/app/customsClearanceInfo/delete', removeObject)
}

export const removeByIdList = function (removeObject) {
  return Http.post('/api/app/customsClearanceInfo/removeByIdList', removeObject)
}
