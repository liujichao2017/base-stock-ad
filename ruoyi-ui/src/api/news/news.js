import request from '@/utils/request'

// 查询新闻资讯列表
export function listNews(query) {
  return request({
    url: '/news/news/list',
    method: 'get',
    params: query
  })
}

// 查询新闻资讯详细
export function getNews(id) {
  return request({
    url: '/news/news/' + id,
    method: 'get'
  })
}

// 新增新闻资讯
export function addNews(data) {
  return request({
    url: '/news/news',
    method: 'post',
    data: data
  })
}

// 修改新闻资讯
export function updateNews(data) {
  return request({
    url: '/news/news',
    method: 'put',
    data: data
  })
}

// 删除新闻资讯
export function delNews(id) {
  return request({
    url: '/news/news/' + id,
    method: 'delete'
  })
}
