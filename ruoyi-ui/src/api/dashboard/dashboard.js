import request from '@/utils/request'

// 统计用户情况
export function countMember(query) {
  return request({
    url: '/dashboard/count/member',
    method: 'get',
    params: query
  })
}

// 统计用户资金
export function countFunds(query) {
  return request({
    url: '/dashboard/count/funds',
    method: 'get',
    params: query
  })
}


// 统计充值情况
export function countRecharge(query) {
  return request({
    url: '/dashboard/count/recharge',
    method: 'get',
    params: query
  })
}


// 统计提现情况
export function countWithdrawal(query) {
  return request({
    url: '/dashboard/count/withdrawal',
    method: 'get',
    params: query
  })
}

