import request from '@/utils/request'

// 查询虚拟币合约委托列表
export function listDelegation(query) {
  return request({
    url: '/coin/delegation/list',
    method: 'get',
    params: query
  })
}

// 查询虚拟币合约委托详细
export function getDelegation(id) {
  return request({
    url: '/coin/delegation/' + id,
    method: 'get'
  })
}

// 新增虚拟币合约委托
export function addDelegation(data) {
  return request({
    url: '/coin/delegation',
    method: 'post',
    data: data
  })
}

// 修改虚拟币合约委托
export function updateDelegation(data) {
  return request({
    url: '/coin/delegation',
    method: 'put',
    data: data
  })
}

// 删除虚拟币合约委托
export function delDelegation(id) {
  return request({
    url: '/coin/delegation/' + id,
    method: 'delete'
  })
}
