import request from '@/utils/request'

// 查询新闻配置列表
export function listConfig(query) {
  return request({
    url: '/news/config/list',
    method: 'get',
    params: query
  })
}

// 查询新闻配置详细
export function getConfig(id) {
  return request({
    url: '/news/config/' + id,
    method: 'get'
  })
}

// 新增新闻配置
export function addConfig(data) {
  return request({
    url: '/news/config',
    method: 'post',
    data: data
  })
}

// 修改新闻配置
export function updateConfig(data) {
  return request({
    url: '/news/config',
    method: 'put',
    data: data
  })
}

// 删除新闻配置
export function delConfig(id) {
  return request({
    url: '/news/config/' + id,
    method: 'delete'
  })
}
