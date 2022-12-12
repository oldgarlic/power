package com.lll.poweradmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lll.poweradmin.model.domain.User;
import com.lll.poweradmin.mapper.UserMapper;
import com.lll.poweradmin.model.vo.UserPageRequest;
import com.lll.poweradmin.service.IDeptService;
import com.lll.poweradmin.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author oldgarlic
 * @since 2022-11-03
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private IDeptService deptService;




    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return token字符串
     */
    @Override
    public String login(String username, String password) {
        // 验证先不做
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,username);
        queryWrapper.eq(User::getPassword,password);
        User user = userMapper.selectOne(queryWrapper);
        // 进行校验
        return "asd";
    }


    @Override
    public IPage<User> userPage(UserPageRequest userPageRequest) {
        Page<User> userPage = userPageRequest.buildPage(User.class);
        QueryWrapper<User> queryWrapper = userPageRequest.build(User.class);
        if(userPageRequest.getDeptId()!=null){
            queryWrapper.in("dept_id",deptService.querySubDeptIds(userPageRequest.getDeptId()));
        }
        return userMapper.selectPage(userPage,queryWrapper);
    }

    @Override
    public User selectUserByUsername(String username) {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getUsername,username);
        return userMapper.selectOne(userLambdaQueryWrapper);
    }
}
