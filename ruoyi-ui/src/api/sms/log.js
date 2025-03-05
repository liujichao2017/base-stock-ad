import request from '@/utils/request'

// 查询短信记录列表
export function listLog(query) {
  return request({
    url: '/sms/log/list',
    method: 'get',
    params: query
  })
}

// 查询短信记录详细
export function getLog(id) {
  return request({
    url: '/sms/log/' + id,
    method: 'get'
  })
}

// 新增短信记录
export function addLog(data) {
  return request({
    url: '/sms/log',
    method: 'post',
    data: data
  })
}

// 修改短信记录
export function updateLog(data) {
  return request({
    url: '/sms/log',
    method: 'put',
    data: data
  })
}

// 删除短信记录
export function delLog(id) {
  return request({
    url: '/sms/log/' + id,
    method: 'delete'
  })
}
