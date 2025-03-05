import request from '@/utils/request'

// 查询客户操作列表
export function listLog(query) {
  return request({
    url: '/audit/log/list',
    method: 'get',
    params: query
  })
}

// 查询客户操作详细
export function getLog(id) {
  return request({
    url: '/audit/log/' + id,
    method: 'get'
  })
}

// 新增客户操作
export function addLog(data) {
  return request({
    url: '/audit/log',
    method: 'post',
    data: data
  })
}

// 修改客户操作
export function updateLog(data) {
  return request({
    url: '/audit/log',
    method: 'put',
    data: data
  })
}

// 删除客户操作
export function delLog(id) {
  return request({
    url: '/audit/log/' + id,
    method: 'delete'
  })
}
