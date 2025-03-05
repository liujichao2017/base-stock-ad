import request from '@/utils/request'

// 查询基金产品列表
export function listFundLists(query) {
  return request({
    url: '/fund/fundLists/list',
    method: 'get',
    params: query
  })
}

// 查询基金产品详细
export function getFundLists(id) {
  return request({
    url: '/fund/fundLists/' + id,
    method: 'get'
  })
}

// 新增基金产品
export function addFundLists(data) {
  return request({
    url: '/fund/fundLists',
    method: 'post',
    data: data
  })
}

// 修改基金产品
export function updateFundLists(data) {
  return request({
    url: '/fund/fundLists',
    method: 'put',
    data: data
  })
}

// 删除基金产品
export function delFundLists(id) {
  return request({
    url: '/fund/fundLists/' + id,
    method: 'delete'
  })
}
