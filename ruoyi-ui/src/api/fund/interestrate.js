import request from '@/utils/request'

// 查询日收益列表
export function listInterestrate(query) {
  return request({
    url: '/interestrate/interestrate/list',
    method: 'get',
    params: query
  })
}

// 查询日收益详细
export function getInterestrate(id) {
  return request({
    url: '/interestrate/interestrate/' + id,
    method: 'get'
  })
}

// 新增日收益
export function addInterestrate(data) {
  return request({
    url: '/interestrate/interestrate',
    method: 'post',
    data: data
  })
}

// 修改日收益
export function updateInterestrate(data) {
  return request({
    url: '/interestrate/interestrate',
    method: 'put',
    data: data
  })
}

// 删除日收益
export function delInterestrate(id) {
  return request({
    url: '/interestrate/interestrate/' + id,
    method: 'delete'
  })
}
