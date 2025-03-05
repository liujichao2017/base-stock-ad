import request from '@/utils/request'

// 查询交易对列表
export function listSymbols(query) {
  return request({
    url: '/coin/symbols/list',
    method: 'get',
    params: query
  })
}

// 查询交易对详细
export function getSymbols(id) {
  return request({
    url: '/coin/symbols/' + id,
    method: 'get'
  })
}

// 新增交易对
export function addSymbols(data) {
  return request({
    url: '/coin/symbols',
    method: 'post',
    data: data
  })
}

// 修改交易对
export function updateSymbols(data) {
  return request({
    url: '/coin/symbols',
    method: 'put',
    data: data
  })
}

// 删除交易对
export function delSymbols(id) {
  return request({
    url: '/coin/symbols/' + id,
    method: 'delete'
  })
}
