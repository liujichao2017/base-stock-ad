import request from '@/utils/request'

// 查询虚拟币杠杆列表
export function listLevel(query) {
  return request({
    url: '/coin/level/list',
    method: 'get',
    params: query
  })
}

// 查询虚拟币杠杆详细
export function getLevel(id) {
  return request({
    url: '/coin/level/' + id,
    method: 'get'
  })
}

// 新增虚拟币杠杆
export function addLevel(data) {
  return request({
    url: '/coin/level',
    method: 'post',
    data: data
  })
}

// 修改虚拟币杠杆
export function updateLevel(data) {
  return request({
    url: '/coin/level',
    method: 'put',
    data: data
  })
}

// 删除虚拟币杠杆
export function delLevel(id) {
  return request({
    url: '/coin/level/' + id,
    method: 'delete'
  })
}
