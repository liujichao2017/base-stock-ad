import request from '@/utils/request'

// 查询客户充值列表
export function listRecharge(query) {
  return request({
    url: '/funds/recharge/list',
    method: 'get',
    params: query
  })
}

// 查询客户充值详细
export function getRecharge(id) {
  return request({
    url: '/funds/recharge/' + id,
    method: 'get'
  })
}

// 新增客户充值
export function addRecharge(data) {
  return request({
    url: '/funds/recharge',
    method: 'post',
    data: data
  })
}

// 修改客户充值
export function updateRecharge(data) {
  return request({
    url: '/funds/recharge',
    method: 'put',
    data: data
  })
}

// 删除客户充值
export function delRecharge(id) {
  return request({
    url: '/funds/recharge/' + id,
    method: 'delete'
  })
}
