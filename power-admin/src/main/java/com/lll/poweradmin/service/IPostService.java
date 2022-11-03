package com.lll.poweradmin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lll.poweradmin.model.domain.Post;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 岗位信息表 服务类
 * </p>
 *
 * @author oldgarlic
 * @since 2022-11-03
 */
public interface IPostService extends IService<Post> {


    /**
     * 分页查询
     *
     * @param post 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<Post> pageQuery(Post post, long current, long size);

}
