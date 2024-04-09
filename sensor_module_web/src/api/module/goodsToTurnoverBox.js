import Http from '@/api/http.js'

export const add = function (addObject) {
  return Http.post('/api/app/transportInfoTurnoverBoxGoods/add', addObject)
}

export const list = function (queryCriteria) {
  return Http.post('/api/app/transportInfoTurnoverBoxGoods/list', queryCriteria)
}

export const update = function (updateObject) {
  return Http.post(
    '/api/app/transportInfoTurnoverBoxGoods/updateById',
    updateObject
  )
}

export const remove = function (removeObject) {
  return Http.post(
    '/api/app/transportInfoTurnoverBoxGoods/delete',
    removeObject
  )
}

export const removeByIdList = function (removeObject) {
  return Http.post(
    '/api/app/transportInfoTurnoverBoxGoods/removeByIdList',
    removeObject
  )
}
