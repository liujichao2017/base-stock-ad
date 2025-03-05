import request from '@/utils/request'

// 查询用户贷款申请列表
export function listLoanRecord(query) {
  return request({
    url: '/loan/loanRecord/list',
    method: 'get',
    params: query
  })
}

// 查询用户贷款申请详细
export function getLoanRecord(id) {
  return request({
    url: '/loan/loanRecord/' + id,
    method: 'get'
  })
}

// 新增用户贷款申请
export function addLoanRecord(data) {
  return request({
    url: '/loan/loanRecord',
    method: 'post',
    data: data
  })
}

// 修改用户贷款申请
export function updateLoanRecord(data) {
  return request({
    url: '/loan/loanRecord',
    method: 'put',
    data: data
  })
}

// 删除用户贷款申请
export function delLoanRecord(id) {
  return request({
    url: '/loan/loanRecord/' + id,
    method: 'delete'
  })
}
