import Http from '../http';

export const add = function(removeObject : any) {
    return Http.post('/api/app/goods/add', removeObject)
}

export const list = function(queryCriteria : any) {
    return Http.post('/api/app/goods/list', queryCriteria)
}

export const update = function(updateObject : any) {
    return Http.post('/api/app/goods/updateById', updateObject) 
}

export const remove = function(removeObject : any) {
    return Http.post('/api/app/goods/delete', removeObject)
}
