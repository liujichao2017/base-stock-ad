import request from '@/utils/request'

// 查询自发币列表
export function listSpontaneous(query) {
  return request({
    url: '/coin/spontaneous/list',
    method: 'get',
    params: query
  })
}

// 查询自发币详细
export function getSpontaneous(id) {
  return request({
    url: '/coin/spontaneous/' + id,
    method: 'get'
  })
}

// 新增自发币
export function addSpontaneous(data) {
  return request({
    url: '/coin/spontaneous',
    method: 'post',
    data: data
  })
}

// 修改自发币
export function updateSpontaneous(data) {
  return request({
    url: '/coin/spontaneous',
    method: 'put',
    data: data
  })
}

// 删除自发币
export function delSpontaneous(id) {
  return request({
    url: '/coin/spontaneous/' + id,
    method: 'delete'
  })
}
