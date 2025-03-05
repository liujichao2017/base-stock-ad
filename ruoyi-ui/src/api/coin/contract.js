import request from '@/utils/request'

// 查询虚拟币合约列表
export function listContract(query) {
  return request({
    url: '/coin/contract/list',
    method: 'get',
    params: query
  })
}

// 查询虚拟币合约详细
export function getContract(id) {
  return request({
    url: '/coin/contract/' + id,
    method: 'get'
  })
}

// 新增虚拟币合约
export function addContract(data) {
  return request({
    url: '/coin/contract',
    method: 'post',
    data: data
  })
}

// 修改虚拟币合约
export function updateContract(data) {
  return request({
    url: '/coin/contract',
    method: 'put',
    data: data
  })
}

// 删除虚拟币合约
export function delContract(id) {
  return request({
    url: '/coin/contract/' + id,
    method: 'delete'
  })
}
