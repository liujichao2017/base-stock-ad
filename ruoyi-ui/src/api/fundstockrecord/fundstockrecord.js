import request from '@/utils/request'

// 查询基金股票子项列表
export function listFundstockrecord(query) {
  return request({
    url: '/fundstockrecord/fundstockrecord/list',
    method: 'get',
    params: query
  })
}

// 查询基金股票子项详细
export function getFundstockrecord(id) {
  return request({
    url: '/fundstockrecord/fundstockrecord/' + id,
    method: 'get'
  })
}

// 新增基金股票子项
export function addFundstockrecord(data) {
  return request({
    url: '/fundstockrecord/fundstockrecord',
    method: 'post',
    data: data
  })
}

// 修改基金股票子项
export function updateFundstockrecord(data) {
  return request({
    url: '/fundstockrecord/fundstockrecord',
    method: 'put',
    data: data
  })
}

// 删除基金股票子项
export function delFundstockrecord(id) {
  return request({
    url: '/fundstockrecord/fundstockrecord/' + id,
    method: 'delete'
  })
}
