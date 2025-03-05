import request from '@/utils/request'

// 查询AI交易记录列表
export function listRecord(query) {
  return request({
    url: '/ai/record/list',
    method: 'get',
    params: query
  })
}

// 查询AI交易记录详细
export function getRecord(id) {
  return request({
    url: '/ai/record/' + id,
    method: 'get'
  })
}

// 新增AI交易记录
export function addRecord(data) {
  return request({
    url: '/ai/record',
    method: 'post',
    data: data
  })
}

// 修改AI交易记录
export function updateRecord(data) {
  return request({
    url: '/ai/record',
    method: 'put',
    data: data
  })
}

// AI建仓
export function aiPosition(data) {
  return request({
    url: '/ai/record/position',
    method: 'put',
    data: data
  })
}

// 删除AI交易记录
export function delRecord(id) {
  return request({
    url: '/ai/record/' + id,
    method: 'delete'
  })
}
