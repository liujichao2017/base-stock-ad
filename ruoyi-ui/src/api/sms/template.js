import request from '@/utils/request'

// 查询短信模版列表
export function listTemplate(query) {
  return request({
    url: '/sms/template/list',
    method: 'get',
    params: query
  })
}

// 查询短信模版详细
export function getTemplate(id) {
  return request({
    url: '/sms/template/' + id,
    method: 'get'
  })
}

// 新增短信模版
export function addTemplate(data) {
  return request({
    url: '/sms/template',
    method: 'post',
    data: data
  })
}

// 修改短信模版
export function updateTemplate(data) {
  return request({
    url: '/sms/template',
    method: 'put',
    data: data
  })
}

// 删除短信模版
export function delTemplate(id) {
  return request({
    url: '/sms/template/' + id,
    method: 'delete'
  })
}
