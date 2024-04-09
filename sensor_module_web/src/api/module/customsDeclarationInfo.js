import Http from '@/api/http.js'

export const add = function (removeObject) {
  return Http.post('/api/app/customsDeclarationInfo/add', removeObject)
}

export const list = function (queryCriteria) {
  return Http.post('/api/app/customsDeclarationInfo/list', queryCriteria)
}

export const info = function (transportInfo) {
  return Http.post(
    '/api/app/customsDeclarationInfo/viewByTransportInfo',
    transportInfo
  )
}

export const update = function (updateObject) {
  return Http.post('/api/app/customsDeclarationInfo/updateById', updateObject)
}

export const remove = function (removeObject) {
  return Http.post('/api/app/customsDeclarationInfo/delete', removeObject)
}

export const removeByIdList = function (removeObject) {
  return Http.post(
    '/api/app/customsDeclarationInfo/removeByIdList',
    removeObject
  )
}
