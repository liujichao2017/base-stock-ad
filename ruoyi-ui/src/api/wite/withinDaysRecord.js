import request from '@/utils/request'

// 查询用户日内交易记录列表
export function listWithinDaysRecord(query) {
  return request({
    url: '/stock/withinDaysRecord/list',
    method: 'get',
    params: query
  })
}

// 查询用户日内交易记录详细
export function getWithinDaysRecord(id) {
  return request({
    url: '/stock/withinDaysRecord/' + id,
    method: 'get'
  })
}

// 新增用户日内交易记录
export function addWithinDaysRecord(data) {
  return request({
    url: '/stock/withinDaysRecord',
    method: 'post',
    data: data
  })
}

// 修改用户日内交易记录
export function updateWithinDaysRecord(data) {
  return request({
    url: '/stock/withinDaysRecord',
    method: 'put',
    data: data
  })
}

// 删除用户日内交易记录
export function delWithinDaysRecord(id) {
  return request({
    url: '/stock/withinDaysRecord/' + id,
    method: 'delete'
  })
}


// 日内建仓
export function createWithinDaysRecord(data) {
  return request({
    url: '/stock/withinDaysRecord/create',
    method: 'post',
    data: data
  })
}