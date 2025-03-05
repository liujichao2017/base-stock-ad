import request from '@/utils/request'

// 查询借劵记录列表
export function listRecord(query) {
  return request({
    url: '/borrow/record/list',
    method: 'get',
    params: query
  })
}

// 查询借劵记录详细
export function getRecord(id) {
  return request({
    url: '/borrow/record/' + id,
    method: 'get'
  })
}

// 新增借劵记录
export function addRecord(data) {
  return request({
    url: '/borrow/record',
    method: 'post',
    data: data
  })
}

// 修改借劵记录
export function updateRecord(data) {
  return request({
    url: '/borrow/record',
    method: 'put',
    data: data
  })
}

// 删除借劵记录
export function delRecord(id) {
  return request({
    url: '/borrow/record/' + id,
    method: 'delete'
  })
}
