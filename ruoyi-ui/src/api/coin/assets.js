import request from '@/utils/request'

// 查询虚拟币资金列表
export function listAssets(query) {
  return request({
    url: '/coin/assets/list',
    method: 'get',
    params: query
  })
}

// 查询虚拟币资金详细
export function getAssets(id) {
  return request({
    url: '/coin/assets/' + id,
    method: 'get'
  })
}

// 新增虚拟币资金
export function addAssets(data) {
  return request({
    url: '/coin/assets',
    method: 'post',
    data: data
  })
}

// 修改虚拟币资金
export function updateAssets(data) {
  return request({
    url: '/coin/assets',
    method: 'put',
    data: data
  })
}

// 删除虚拟币资金
export function delAssets(id) {
  return request({
    url: '/coin/assets/' + id,
    method: 'delete'
  })
}
