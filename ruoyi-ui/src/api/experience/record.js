import request from '@/utils/request'

// 查询体验金使用记录列表
export function listRecord(query) {
  return request({
    url: '/experience/record/list',
    method: 'get',
    params: query
  })
}

// 查询体验金使用记录详细
export function getRecord(id) {
  return request({
    url: '/experience/record/' + id,
    method: 'get'
  })
}

// 新增体验金使用记录
export function addRecord(data) {
  return request({
    url: '/experience/record',
    method: 'post',
    data: data
  })
}

// 修改体验金使用记录
export function updateRecord(data) {
  return request({
    url: '/experience/record',
    method: 'put',
    data: data
  })
}

// 删除体验金使用记录
export function delRecord(id) {
  return request({
    url: '/experience/record/' + id,
    method: 'delete'
  })
}
