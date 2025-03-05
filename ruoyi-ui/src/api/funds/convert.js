import request from '@/utils/request'

// 查询兑换记录列表
export function listConvert(query) {
  return request({
    url: '/funds/convert/list',
    method: 'get',
    params: query
  })
}

// 查询兑换记录详细
export function getConvert(id) {
  return request({
    url: '/funds/convert/' + id,
    method: 'get'
  })
}

// 新增兑换记录
export function addConvert(data) {
  return request({
    url: '/funds/convert',
    method: 'post',
    data: data
  })
}

// 修改兑换记录
export function updateConvert(data) {
  return request({
    url: '/funds/convert',
    method: 'put',
    data: data
  })
}

// 删除兑换记录
export function delConvert(id) {
  return request({
    url: '/funds/convert/' + id,
    method: 'delete'
  })
}
