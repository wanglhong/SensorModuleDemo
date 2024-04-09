import Http from '@/api/http.js'

export const add = function (removeObject) {
  return Http.post('/api/app/declarationInfoBusinessFile/add', removeObject)
}

export const list = function (queryCriteria) {
  return Http.post('/api/app/declarationInfoBusinessFile/list', queryCriteria)
}

export const update = function (updateObject) {
  return Http.post(
    '/api/app/declarationInfoBusinessFile/updateById',
    updateObject
  )
}

export const remove = function (removeObject) {
  return Http.post('/api/app/declarationInfoBusinessFile/delete', removeObject)
}

export const removeByIdList = function (removeObject) {
  return Http.post(
    '/api/app/declarationInfoBusinessFile/removeByIdList',
    removeObject
  )
}

export const delFile = function (delFile) {
  return Http.post('/api/app/declarationInfoBusinessFile/deleteFile', delFile)
}
