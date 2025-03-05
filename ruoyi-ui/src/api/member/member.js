import request from '@/utils/request'

// 查询会员列表
export function listMember(query) {
  return request({
    url: '/member/member/list',
    method: 'get',
    params: query
  })
}

// 查询会员详细
export function getMember(id) {
  return request({
    url: '/member/member/' + id,
    method: 'get'
  })
}

// 新增会员
export function addMember(data) {
  return request({
    url: '/member/member',
    method: 'post',
    data: data
  })
}

// 修改会员
export function updateMember(data) {
  return request({
    url: '/member/member',
    method: 'put',
    data: data
  })
}

// 删除会员
export function delMember(id) {
  return request({
    url: '/member/member/' + id,
    method: 'delete'
  })
}

// 修改资金
export function updateMemberAmt(data) {
  return request({
    url: '/member/member/updateAmt',
    method: 'put',
    data: data
  })
}

// 修改资金
export function updateMemberRealName(data) {
  return request({
    url: '/member/member/realName',
    method: 'put',
    data: data
  })
}

// 修改银行卡
export function updateMemberBankCard(data) {
  return request({
    url: '/member/member/bankCard',
    method: 'put',
    data: data
  })
}

// 删除银行卡
export function updateMemberBankCardReset(data) {
  return request({
    url: '/member/member/bankCardReset',
    method: 'put',
    data: data
  })
}

// 查询会员详细
export function seaMember(phone) {
  return request({
    url: '/member/member/search/' + phone,
    method: 'get'
  })
}

// 发送短信
export function sendSms(data) {
  return request({
    url: '/member/member/sendSms',
    method: 'put',
    data: data
  })
}
