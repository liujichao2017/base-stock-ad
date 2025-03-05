import request from '@/utils/request'

// 查询涨停列表列表
export function listList(query) {
  return request({
    url: '/dailylimit/list/list',
    method: 'get',
    params: query
  })
}

// 查询涨停列表详细
export function getList(id) {
  return request({
    url: '/dailylimit/list/' + id,
    method: 'get'
  })
}

// 新增涨停列表
export function addList(data) {
  return request({
    url: '/dailylimit/list',
    method: 'post',
    data: data
  })
}

// 修改涨停列表
export function updateList(data) {
  return request({
    url: '/dailylimit/list',
    method: 'put',
    data: data
  })
}

// 删除涨停列表
export function delList(id) {
  return request({
    url: '/dailylimit/list/' + id,
    method: 'delete'
  })
}
