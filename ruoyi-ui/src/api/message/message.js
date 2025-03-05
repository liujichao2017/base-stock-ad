import request from '@/utils/request'

// 查询客户消息列表
export function listMessage(query) {
  return request({
    url: '/message/message/list',
    method: 'get',
    params: query
  })
}

// 查询客户消息详细
export function getMessage(id) {
  return request({
    url: '/message/message/' + id,
    method: 'get'
  })
}

// 新增客户消息
export function addMessage(data) {
  return request({
    url: '/message/message',
    method: 'post',
    data: data
  })
}

// 修改客户消息
export function updateMessage(data) {
  return request({
    url: '/message/message',
    method: 'put',
    data: data
  })
}

// 删除客户消息
export function delMessage(id) {
  return request({
    url: '/message/message/' + id,
    method: 'delete'
  })
}
