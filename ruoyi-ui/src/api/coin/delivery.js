import request from '@/utils/request'

// 查询虚拟币交割方案列表
export function listDelivery(query) {
  return request({
    url: '/coin/delivery/list',
    method: 'get',
    params: query
  })
}

// 查询虚拟币交割方案详细
export function getDelivery(id) {
  return request({
    url: '/coin/delivery/' + id,
    method: 'get'
  })
}

// 新增虚拟币交割方案
export function addDelivery(data) {
  return request({
    url: '/coin/delivery',
    method: 'post',
    data: data
  })
}

// 修改虚拟币交割方案
export function updateDelivery(data) {
  return request({
    url: '/coin/delivery',
    method: 'put',
    data: data
  })
}

// 删除虚拟币交割方案
export function delDelivery(id) {
  return request({
    url: '/coin/delivery/' + id,
    method: 'delete'
  })
}
