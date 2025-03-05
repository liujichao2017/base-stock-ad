import request from '@/utils/request'

// 查询通用配置列表
export function listCommon(query) {
  return request({
    url: '/config/common/list',
    method: 'get',
    params: query
  })
}

// 查询配置组
export function getGroup() {
  return request({
    url: '/config/common/group',
    method: 'get'
  })
}

// 查询通用配置详细
export function getCommon(id) {
  return request({
    url: '/config/common/' + id,
    method: 'get'
  })
}

// 新增通用配置
export function addCommon(data) {
  return request({
    url: '/config/common',
    method: 'post',
    data: data
  })
}

// 修改通用配置
export function updateCommon(data) {
  return request({
    url: '/config/common',
    method: 'put',
    data: data
  })
}

// 删除通用配置
export function delCommon(id) {
  return request({
    url: '/config/common/' + id,
    method: 'delete'
  })
}
