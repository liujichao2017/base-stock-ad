import request from '@/utils/request'

// 查询节假日列表
export function listClosing(query) {
  return request({
    url: '/stock/closing/list',
    method: 'get',
    params: query
  })
}

// 查询节假日详细
export function getClosing(id) {
  return request({
    url: '/stock/closing/' + id,
    method: 'get'
  })
}

// 新增节假日
export function addClosing(data) {
  return request({
    url: '/stock/closing',
    method: 'post',
    data: data
  })
}

// 修改节假日
export function updateClosing(data) {
  return request({
    url: '/stock/closing',
    method: 'put',
    data: data
  })
}

// 删除节假日
export function delClosing(id) {
  return request({
    url: '/stock/closing/' + id,
    method: 'delete'
  })
}
