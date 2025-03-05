import request from '@/utils/request'

// 查询体验金列表
export function listExperience(query) {
  return request({
    url: '/experience/experience/list',
    method: 'get',
    params: query
  })
}

// 查询体验金详细
export function getExperience(id) {
  return request({
    url: '/experience/experience/' + id,
    method: 'get'
  })
}

// 新增体验金
export function addExperience(data) {
  return request({
    url: '/experience/experience',
    method: 'post',
    data: data
  })
}

// 修改体验金
export function updateExperience(data) {
  return request({
    url: '/experience/experience',
    method: 'put',
    data: data
  })
}

// 删除体验金
export function delExperience(id) {
  return request({
    url: '/experience/experience/' + id,
    method: 'delete'
  })
}
