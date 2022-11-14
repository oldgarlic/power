
import request from '@/utils/request'


export function addOrUpdatePost(data){
    return request({
        url: '/power/post',
        method: 'post',
        data
    })
}


export function getList() {
    return request({
        url: '/power/post/list',
        method: 'get'
    })
}

export function deletePost(postId) {
    return request({
        url: '/power/post/'+postId,
        method: 'delete'
    })
}



