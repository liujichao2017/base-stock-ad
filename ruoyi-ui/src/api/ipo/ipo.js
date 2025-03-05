import request from '@/utils/request'

// 查询IPO(新股)列表
export function listIpo(query) {
  return request({
    url: '/stock/ipo/list',
    method: 'get',
    params: query
  })
}

// 查询IPO(新股)详细
export function getIpo(id) {
  return request({
    url: '/stock/ipo/' + id,
    method: 'get'
  })
}

// 新增IPO(新股)
export function addIpo(data) {
  return request({
    url: '/stock/ipo',
    method: 'post',
    data: data
  })
}

// 修改IPO(新股)
export function updateIpo(data) {
  return request({
    url: '/stock/ipo',
    method: 'put',
    data: data
  })
}

// 删除IPO(新股)
export function delIpo(id) {
  return request({
    url: '/stock/ipo/' + id,
    method: 'delete'
  })
}
