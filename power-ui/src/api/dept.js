
import request from '@/utils/request'


export function addOrUpdateDept(data){
    return request({
        url: '/power/dept',
        method: 'post',
        data
    })
}


export function getList(data) {
    return request({
        url: '/power/dept/list',
        method: 'post',
        data
    })
}

export function deleteDept(deptId) {
    return request({
        url: '/power/dept/'+deptId,
        method: 'delete'
    })
}



