import request from '@/utils/request'



// 查询虚拟币K线列表
export function listKlineByApi(query) {
  return request({
    url: Global.appUrl+'/api/coin/symbols/kline',
    method: 'get',
    params: query
  })
}

// 查询虚拟币K线列表
export function listKline(query) {
  return request({
    url: '/coin/kline/list',
    method: 'get',
    params: query
  })
}

// 查询虚拟币K线详细
export function getKline(id) {
  return request({
    url: '/coin/kline/' + id,
    method: 'get'
  })
}

// 新增虚拟币K线
export function addKline(data) {
  return request({
    url: '/coin/kline',
    method: 'post',
    data: data
  })
}

// 修改虚拟币K线
export function updateKline(data) {
  return request({
    url: '/coin/kline',
    method: 'put',
    data: data
  })
}

// 删除虚拟币K线
export function delKline(id) {
  return request({
    url: '/coin/kline/' + id,
    method: 'delete'
  })
}
