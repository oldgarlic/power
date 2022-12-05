
import request from '@/utils/request'


export function addOrUpdatePost(data){
    return request({
        url: '/power/post',
        method: 'post',
        data
    })
}


export function getPostList(data = {sort: 'post_id'}) {
    return request({
        url: '/power/post/page',
        method: 'post',
        data
    })
}

export function deletePost(postId) {
    return request({
        url: '/power/post/'+postId,
        method: 'delete'
    })
}



