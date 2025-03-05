import request from '@/utils/request'

// 查询日内交易配置列表
export function listWithinDaysSite(query) {
  return request({
    url: '/stock/withinDaysSite/list',
    method: 'get',
    params: query
  })
}

// 查询日内交易配置详细
export function getWithinDaysSite(id) {
  return request({
    url: '/stock/withinDaysSite/' + id,
    method: 'get'
  })
}

// 新增日内交易配置
export function addWithinDaysSite(data) {
  return request({
    url: '/stock/withinDaysSite',
    method: 'post',
    data: data
  })
}

// 修改日内交易配置
export function updateWithinDaysSite(data) {
  return request({
    url: '/stock/withinDaysSite',
    method: 'put',
    data: data
  })
}

// 删除日内交易配置
export function delWithinDaysSite(id) {
  return request({
    url: '/stock/withinDaysSite/' + id,
    method: 'delete'
  })
}
