import request from '@/utils/request'

// 查询存股借卷配置列表
export function listProduct(query) {
  return request({
    url: '/borrow/product/list',
    method: 'get',
    params: query
  })
}

// 查询存股借卷配置详细
export function getProduct(id) {
  return request({
    url: '/borrow/product/' + id,
    method: 'get'
  })
}

// 新增存股借卷配置
export function addProduct(data) {
  return request({
    url: '/borrow/product',
    method: 'post',
    data: data
  })
}

// 修改存股借卷配置
export function updateProduct(data) {
  return request({
    url: '/borrow/product',
    method: 'put',
    data: data
  })
}

// 删除存股借卷配置
export function delProduct(id) {
  return request({
    url: '/borrow/product/' + id,
    method: 'delete'
  })
}
