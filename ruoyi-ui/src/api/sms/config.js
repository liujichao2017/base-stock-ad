import request from '@/utils/request'

// 查询短信配置列表
export function listConfig(query) {
  return request({
    url: '/sms/config/list',
    method: 'get',
    params: query
  })
}

// 查询短信配置详细
export function getConfig(id) {
  return request({
    url: '/sms/config/' + id,
    method: 'get'
  })
}

// 新增短信配置
export function addConfig(data) {
  return request({
    url: '/sms/config',
    method: 'post',
    data: data
  })
}

// 修改短信配置
export function updateConfig(data) {
  return request({
    url: '/sms/config',
    method: 'put',
    data: data
  })
}

// 删除短信配置
export function delConfig(id) {
  return request({
    url: '/sms/config/' + id,
    method: 'delete'
  })
}
