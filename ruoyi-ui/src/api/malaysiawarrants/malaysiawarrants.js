import request from '@/utils/request'

// 查询malaysiawarrants列表
export function listMalaysiawarrants(query) {
  return request({
    url: '/malaysiawarrants/malaysiawarrants/list',
    method: 'get',
    params: query
  })
}

// 查询malaysiawarrants详细
export function getMalaysiawarrants(dwSymbol) {
  return request({
    url: '/malaysiawarrants/malaysiawarrants/' + dwSymbol,
    method: 'get'
  })
}

// 新增malaysiawarrants
export function addMalaysiawarrants(data) {
  return request({
    url: '/malaysiawarrants/malaysiawarrants',
    method: 'post',
    data: data
  })
}

// 修改malaysiawarrants
export function updateMalaysiawarrants(data) {
  return request({
    url: '/malaysiawarrants/malaysiawarrants',
    method: 'put',
    data: data
  })
}

// 删除malaysiawarrants
export function delMalaysiawarrants(dwSymbol) {
  return request({
    url: '/malaysiawarrants/malaysiawarrants/' + dwSymbol,
    method: 'delete'
  })
}
