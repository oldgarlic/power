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

export function list(data) {
  return request({
    url: '/power/user/page',
    method: 'post',
    data
  })
}

export function addOrUpdateUser(data) {
  return request({
    url: '/power/user',
    method: 'post',
    data
  })
}

export function deleteUserByIds(userIds) {
  return request({
    url: '/power/user/'+userIds,
    method: 'delete'
  })
}

export function updateUserPwd(data){
  return request({
    url: '/power/user/updatePwd',
    method: 'put',
    params: data
  })
}

export function getUser(){
  
}
