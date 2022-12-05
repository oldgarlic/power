package com.lll.poweradmin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lll.poweradmin.common.Result;
import com.lll.poweradmin.model.domain.User;
import com.lll.poweradmin.common.PageRequest;
import com.lll.poweradmin.model.request.UserLoginRequest;
import com.lll.poweradmin.model.vo.UserPageRequest;
import com.lll.poweradmin.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author oldgarlic
 * @since 2022-11-03
 */
@Api(tags = "用户信息表对象功能接口")
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    @ApiOperation("用户登录")
    @PostMapping("login")
    public Result<String> login(@RequestBody UserLoginRequest userLoginRequest){
        // 拿到请求写回token
        String token = "asd";
        // 0.参数校验
        // 1. 校对用户名和密码

        // 2. 返回token
        // 记录登录事件
        return Result.ok(token);
    }

    @ApiOperation("获取个人用户信息")
    @GetMapping("info")
    public Result<User> info(String token){
        // header里面拿token，cookie
        User user = User.builder().avatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif").username("admin").password("admin").build();
        return Result.ok(user);
    }

    @ApiOperation("用户登出")
    @PostMapping("logout")
    public Result<Boolean> logout(){
        return Result.ok(true);
    }

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation("通过ID查询单条数据")
    @GetMapping("{id}")
    public ResponseEntity<User> queryById(@PathVariable("id") long id){
        return ResponseEntity.ok(userService.getById(id));
    }

    /**
     * 分页查询
     * @param userPageRequest 查询条件
     * @return 分页结果
     */
    @ApiOperation("分页查询")
    @PostMapping("page")
    public Result<IPage<User>> pageQuery(@RequestBody UserPageRequest userPageRequest){
        // 查询部门下的所有部门id
        IPage<User> userPage = userService.userPage(userPageRequest);
        return Result.ok(userPage);
    }

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @ApiOperation("新增数据")
    @PostMapping
    public Result<?> add(@RequestBody User user){
        if(user.getUserId()!=null){
            userService.updateById(user);
        }else{
            userService.save(user);
        }
        // 这里需要增加维护关系
        return Result.ok();
    }

    /**
     * 更新数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @ApiOperation("更新数据")
    @PutMapping
    public Result<Boolean> edit(@RequestBody User user){
        return Result.ok(userService.updateById(user));
    }

    /**
     * 通过主键删除数据
     *
     * @param userIds 主键
     * @return 是否成功
     */
    @ApiOperation("通过主键删除数据")
    @DeleteMapping("{userIds}")
    public Result<Boolean> deleteByIds(@PathVariable("userIds") Long[] userIds){
        ArrayList<Long> userIdList = new ArrayList<>();
        Collections.addAll(userIdList,userIds);
        return Result.ok(userService.removeBatchByIds(userIdList));
    }
}
