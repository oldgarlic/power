package com.lll.poweradmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lll.poweradmin.model.domain.Dept;
import com.lll.poweradmin.model.domain.Post;
import com.lll.poweradmin.model.domain.User;
import com.lll.poweradmin.mapper.UserMapper;
import com.lll.poweradmin.model.vo.UserPageRequest;
import com.lll.poweradmin.model.vo.UserVO;
import com.lll.poweradmin.service.IDeptService;
import com.lll.poweradmin.service.IPostService;
import com.lll.poweradmin.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    @Resource
    private IPostService postService;


    @Override
    public void updateUserPassword(long userId, String password) {
        LambdaUpdateWrapper<User> userUpdateWrapper = new LambdaUpdateWrapper<>();
        userUpdateWrapper.set(User::getPassword,password);
        userUpdateWrapper.eq(User::getUserId,userId);
        this.update(userUpdateWrapper);
    }

    @Override
    public void uploadAvatar(MultipartFile image) {
        // TODO:
        // 1、传到OSS服务器上
        // 2、更新到数据中
    }

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

    @Override
    public UserVO selectUserById(long userId) {
        User user = getById(userId);
        UserVO userVO = new ModelMapper().map(user, UserVO.class);
        Post post = postService.getById(user.getPostId());
        Dept dept = deptService.getById(user.getDeptId());
        userVO.setPostName(post.getPostName());
        userVO.setDeptName(dept.getDeptName());
        return userVO;
    }
}
