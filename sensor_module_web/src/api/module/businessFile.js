import Http from '@/api/http.js'
import Download from '@/api/download.js'

export const add = function (removeObject) {
  return Http.post('/api/app/businessFile/add', removeObject)
}

export const list = function (queryCriteria) {
  return Http.post('/api/app/businessFile/list', queryCriteria)
}

export const update = function (updateObject) {
  return Http.post('/api/app/businessFile/updateById', updateObject)
}

export const remove = function (removeObject) {
  return Http.post('/api/app/businessFile/delete', removeObject)
}

export const removeByIdList = function (removeObject) {
  return Http.post('/api/app/businessFile/removeByIdList', removeObject)
}

export const uploadFile = function (uploadFile) {
  return Http.post('/api/app/businessFile/upload', uploadFile, {
    headers: { 'Content-Type': 'multipart/form-data' },
  })
}

export const downloadFile = function (data, filename) {
  return Download.download('/api/app/businessFile/download', data, filename)
}
