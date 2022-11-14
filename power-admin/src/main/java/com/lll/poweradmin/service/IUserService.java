package com.lll.poweradmin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lll.poweradmin.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author oldgarlic
 * @since 2022-11-03
 */

public interface IUserService extends IService<User> {
    /**
     *  用户登录
     * @param username  用户名
     * @param password  密码
     * @return token字符串
     */
    String login(String username,String password);

    //User info(String )
}
