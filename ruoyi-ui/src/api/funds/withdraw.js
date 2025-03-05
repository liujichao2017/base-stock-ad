import request from '@/utils/request'

// 查询用户提现列表
export function listWithdraw(query) {
  return request({
    url: '/funds/withdraw/list',
    method: 'get',
    params: query
  })
}

// 查询用户提现详细
export function getWithdraw(id) {
  return request({
    url: '/funds/withdraw/' + id,
    method: 'get'
  })
}

// 新增用户提现
export function addWithdraw(data) {
  return request({
    url: '/funds/withdraw',
    method: 'post',
    data: data
  })
}

// 修改用户提现
export function updateWithdraw(data) {
  return request({
    url: '/funds/withdraw',
    method: 'put',
    data: data
  })
}

// 删除用户提现
export function delWithdraw(id) {
  return request({
    url: '/funds/withdraw/' + id,
    method: 'delete'
  })
}
