import request from '@/utils/request'

// 查询贷款配置列表
export function listLoanConfig(query) {
  return request({
    url: '/loan/loanConfig/list',
    method: 'get',
    params: query
  })
}

// 查询贷款配置详细
export function getLoanConfig(id) {
  return request({
    url: '/loan/loanConfig/' + id,
    method: 'get'
  })
}

// 新增贷款配置
export function addLoanConfig(data) {
  return request({
    url: '/loan/loanConfig',
    method: 'post',
    data: data
  })
}

// 修改贷款配置
export function updateLoanConfig(data) {
  return request({
    url: '/loan/loanConfig',
    method: 'put',
    data: data
  })
}

// 删除贷款配置
export function delLoanConfig(id) {
  return request({
    url: '/loan/loanConfig/' + id,
    method: 'delete'
  })
}
