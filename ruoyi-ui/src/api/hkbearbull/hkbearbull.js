import request from '@/utils/request'

// 查询牛熊市股列表
export function listHkbearbull(query) {
  return request({
    url: '/hkbearbull/hkbearbull/list',
    method: 'get',
    params: query
  })
}

// 查询牛熊市股详细
export function getHkbearbull(sym) {
  return request({
    url: '/hkbearbull/hkbearbull/' + sym,
    method: 'get'
  })
}

// 新增牛熊市股
export function addHkbearbull(data) {
  return request({
    url: '/hkbearbull/hkbearbull',
    method: 'post',
    data: data
  })
}

// 修改牛熊市股
export function updateHkbearbull(data) {
  return request({
    url: '/hkbearbull/hkbearbull',
    method: 'put',
    data: data
  })
}

// 删除牛熊市股
export function delHkbearbull(sym) {
  return request({
    url: '/hkbearbull/hkbearbull/' + sym,
    method: 'delete'
  })
}
