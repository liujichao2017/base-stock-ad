import request from '@/utils/request'

// 查询otc(大宗)列表
export function listOtc(query) {
  return request({
    url: '/stock/otc/list',
    method: 'get',
    params: query
  })
}

// 查询otc(大宗)详细
export function getOtc(id) {
  return request({
    url: '/stock/otc/' + id,
    method: 'get'
  })
}

// 新增otc(大宗)
export function addOtc(data) {
  return request({
    url: '/stock/otc',
    method: 'post',
    data: data
  })
}

// 修改otc(大宗)
export function updateOtc(data) {
  return request({
    url: '/stock/otc',
    method: 'put',
    data: data
  })
}

// 删除otc(大宗)
export function delOtc(id) {
  return request({
    url: '/stock/otc/' + id,
    method: 'delete'
  })
}
