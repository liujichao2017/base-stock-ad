import request from '@/utils/request'

// 查询资金动态列表
export function listFundslog(query) {
  return request({
    url: '/funds/fundslog/list',
    method: 'get',
    params: query
  })
}

// 查询资金动态详细
export function getFundslog(id) {
  return request({
    url: '/funds/fundslog/' + id,
    method: 'get'
  })
}

// 新增资金动态
export function addFundslog(data) {
  return request({
    url: '/funds/fundslog',
    method: 'post',
    data: data
  })
}

// 修改资金动态
export function updateFundslog(data) {
  return request({
    url: '/funds/fundslog',
    method: 'put',
    data: data
  })
}

// 删除资金动态
export function delFundslog(id) {
  return request({
    url: '/funds/fundslog/' + id,
    method: 'delete'
  })
}
