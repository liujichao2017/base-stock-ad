import request from '@/utils/request'

// 查询IPO(新股)申购记录列表
export function listIpoRecord(query) {
  return request({
    url: '/stock/ipoRecord/list',
    method: 'get',
    params: query
  })
}

// 查询IPO(新股)申购记录详细
export function getIpoRecord(id) {
  return request({
    url: '/stock/ipoRecord/' + id,
    method: 'get'
  })
}

// 新增IPO(新股)申购记录
export function addIpoRecord(data) {
  return request({
    url: '/stock/ipoRecord',
    method: 'post',
    data: data
  })
}

// 修改IPO(新股)申购记录
export function updateIpoRecord(data) {
  return request({
    url: '/stock/ipoRecord',
    method: 'put',
    data: data
  })
}

// 删除IPO(新股)申购记录
export function delIpoRecord(id) {
  return request({
    url: '/stock/ipoRecord/' + id,
    method: 'delete'
  })
}

// 审批IPO(新股)申购记录
export function examIpoRecord(data) {
  return request({
    url: '/stock/ipoRecord/exam',
    method: 'post',
    data: data
  })
}

// 划转IPO(新股)申购记录
export function transferIpoRecord(id) {
  return request({
    url: '/stock/ipoRecord/transfer/'+id,
    method: 'post',
  })
}

// 转持仓IPO(新股)申购记录
export function traPosIpoRecord(id) {
  return request({
    url: '/stock/ipoRecord/traPos/'+id,
    method: 'post',
  })
}

// 退款IPO(新股)申购记录
export function refundIpoRecord(id) {
  return request({
    url: '/stock/ipoRecord/refund/'+id,
    method: 'post',
  })
}