import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/power/user/login',
    method: 'post',
    data
  })
}

export function getInfo(token) {
  return request({
    url: '/power/user/info',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/power/user/logout',
    method: 'post'
  })
}
