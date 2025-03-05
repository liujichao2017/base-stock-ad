import request from '@/utils/request'

// 查询市场列表
export function listMarket(query) {
  return request({
    url: '/market/market/list',
    method: 'get',
    params: query
  })
}


export function listMarketByAsync(query) {
  return new Promise(resolve => {
    listMarket(query).then(response => {
      resolve(response.rows)
    });
  });
}


// 查询市场详细
export function getMarket(id) {
  return request({
    url: '/market/market/' + id,
    method: 'get'
  })
}

// 新增市场
export function addMarket(data) {
  return request({
    url: '/market/market',
    method: 'post',
    data: data
  })
}

// 修改市场
export function updateMarket(data) {
  return request({
    url: '/market/market',
    method: 'put',
    data: data
  })
}

// 删除市场
export function delMarket(id) {
  return request({
    url: '/market/market/' + id,
    method: 'delete'
  })
}
