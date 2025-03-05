import request from '@/utils/request'

// 查询股票产品列表
export function listStock(query) {
  return request({
    url: '/stock/stock/list',
    method: 'get',
    params: query
  })
}

// 查询股票产品详细
export function getStock(id) {
  return request({
    url: '/stock/stock/' + id,
    method: 'get'
  })
}

// 新增股票产品
export function addStock(data) {
  return request({
    url: '/stock/stock',
    method: 'post',
    data: data
  })
}

// 修改股票产品
export function updateStock(data) {
  return request({
    url: '/stock/stock',
    method: 'put',
    data: data
  })
}

// 删除股票产品
export function delStock(id) {
  return request({
    url: '/stock/stock/' + id,
    method: 'delete'
  })
}

// 搜索股票最新行情
export function getLastStock(gid) {
  return request({
    url: '/stock/stock/getLastStock/' + gid,
    method: 'get'
  })
}