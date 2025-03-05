import request from '@/utils/request'

// 查询杠杆配置列表
export function listStocklevel(query) {
  return request({
    url: '/stocklevel/stocklevel/list',
    method: 'get',
    params: query
  })
}

// 查询杠杆配置详细
export function getStocklevel(id) {
  return request({
    url: '/stocklevel/stocklevel/' + id,
    method: 'get'
  })
}

// 新增杠杆配置
export function addStocklevel(data) {
  return request({
    url: '/stocklevel/stocklevel',
    method: 'post',
    data: data
  })
}

// 修改杠杆配置
export function updateStocklevel(data) {
  return request({
    url: '/stocklevel/stocklevel',
    method: 'put',
    data: data
  })
}

// 删除杠杆配置
export function delStocklevel(id) {
  return request({
    url: '/stocklevel/stocklevel/' + id,
    method: 'delete'
  })
}
