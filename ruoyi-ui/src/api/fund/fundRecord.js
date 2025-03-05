import request from '@/utils/request'

// 查询基金购买记录列表
export function listFundRecord(query) {
  return request({
    url: '/fund/fundRecord/list',
    method: 'get',
    params: query
  })
}

// 查询基金购买记录详细
export function getFundRecord(id) {
  return request({
    url: '/fund/fundRecord/' + id,
    method: 'get'
  })
}

// 新增基金购买记录
export function addFundRecord(data) {
  return request({
    url: '/fund/fundRecord',
    method: 'post',
    data: data
  })
}

// 修改基金购买记录
export function updateFundRecord(data) {
  return request({
    url: '/fund/fundRecord',
    method: 'put',
    data: data
  })
}

// 删除基金购买记录
export function delFundRecord(id) {
  return request({
    url: '/fund/fundRecord/' + id,
    method: 'delete'
  })
}
