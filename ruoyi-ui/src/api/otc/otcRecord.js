import request from '@/utils/request'

// 查询otc(大宗)记录列表
export function listOtcRecord(query) {
  return request({
    url: '/stock/otcRecord/list',
    method: 'get',
    params: query
  })
}

// 查询otc(大宗)记录详细
export function getOtcRecord(id) {
  return request({
    url: '/stock/otcRecord/' + id,
    method: 'get'
  })
}

// 新增otc(大宗)记录
export function addOtcRecord(data) {
  return request({
    url: '/stock/otcRecord',
    method: 'post',
    data: data
  })
}

// 修改otc(大宗)记录
export function updateOtcRecord(data) {
  return request({
    url: '/stock/otcRecord',
    method: 'put',
    data: data
  })
}

// 删除otc(大宗)记录
export function delOtcRecord(id) {
  return request({
    url: '/stock/otcRecord/' + id,
    method: 'delete'
  })
}
// 审批otc(大宗)记录
export function examOtcRecord(data) {
  return request({
    url: '/stock/otcRecord/exam',
    method: 'post',
    data: data
  })
}