import request from '@/utils/request'

// 查询公告列表
export function listSitemessage(query) {
  return request({
    url: '/sitemessage/sitemessage/list',
    method: 'get',
    params: query
  })
}

// 查询公告详细
export function getSitemessage(id) {
  return request({
    url: '/sitemessage/sitemessage/' + id,
    method: 'get'
  })
}

// 新增公告
export function addSitemessage(data) {
  return request({
    url: '/sitemessage/sitemessage',
    method: 'post',
    data: data
  })
}

// 修改公告
export function updateSitemessage(data) {
  return request({
    url: '/sitemessage/sitemessage',
    method: 'put',
    data: data
  })
}

// 删除公告
export function delSitemessage(id) {
  return request({
    url: '/sitemessage/sitemessage/' + id,
    method: 'delete'
  })
}
