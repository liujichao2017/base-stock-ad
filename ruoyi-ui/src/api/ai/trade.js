import request from '@/utils/request'

// 查询AI交易列表
export function listTrade(query) {
  return request({
    url: '/ai/trade/list',
    method: 'get',
    params: query
  })
}

// 查询AI交易详细
export function getTrade(id) {
  return request({
    url: '/ai/trade/' + id,
    method: 'get'
  })
}

// 新增AI交易
export function addTrade(data) {
  return request({
    url: '/ai/trade',
    method: 'post',
    data: data
  })
}

// 修改AI交易
export function updateTrade(data) {
  return request({
    url: '/ai/trade',
    method: 'put',
    data: data
  })
}

// 删除AI交易
export function delTrade(id) {
  return request({
    url: '/ai/trade/' + id,
    method: 'delete'
  })
}
