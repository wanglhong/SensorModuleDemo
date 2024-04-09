import Http from '@/api/http.js'

export const add = function (removeObject) {
  return Http.post('/api/app/clearanceInfoBusinessFile/add', removeObject)
}

export const list = function (queryCriteria) {
  return Http.post('/api/app/clearanceInfoBusinessFile/list', queryCriteria)
}

export const update = function (updateObject) {
  return Http.post('/api/app/clearanceInfoBusinessFile/updateById', updateObject)
}

export const remove = function (removeObject) {
  return Http.post('/api/app/clearanceInfoBusinessFile/delete', removeObject)
}

export const removeByIdList = function (removeObject) {
  return Http.post('/api/app/clearanceInfoBusinessFile/removeByIdList', removeObject)
}

export const delFile = function (delFile) {
  return Http.post('/api/app/clearanceInfoBusinessFile/deleteFile', delFile)
}
