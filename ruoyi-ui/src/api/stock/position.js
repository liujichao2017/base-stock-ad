import request from '@/utils/request'

// 查询用户持仓列表
export function listPosition(query) {
  return request({
    url: '/stock/position/list',
    method: 'get',
    params: query
  })
}

// 查询用户持仓详细
export function getPosition(id) {
  return request({
    url: '/stock/position/' + id,
    method: 'get'
  })
}

// 新增用户持仓
export function addPosition(data) {
  return request({
    url: '/stock/position',
    method: 'post',
    data: data
  })
}

// 新增用户持仓
export function sellPosition(data) {
  return request({
    url: '/stock/position/sell',
    method: 'post',
    data: data
  })
}

// 修改用户持仓
export function updatePosition(data) {
  return request({
    url: '/stock/position',
    method: 'put',
    data: data
  })
}

// 删除用户持仓
export function delPosition(id) {
  return request({
    url: '/stock/position/' + id,
    method: 'delete'
  })
}
