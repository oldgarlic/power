package com.lll.poweradmin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lll.poweradmin.common.Result;
import com.lll.poweradmin.core.service.LoginService;
import com.lll.poweradmin.model.domain.User;
import com.lll.poweradmin.model.dto.LoginUser;
import com.lll.poweradmin.model.vo.UserLoginRequest;
import com.lll.poweradmin.model.vo.UserPageRequest;
import com.lll.poweradmin.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;

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

    @Resource
    private LoginService loginService;

    @ApiOperation("用户登录")
    @PostMapping("login")
    public Result<String> login(@RequestBody @Valid UserLoginRequest userLoginRequest){
        String token = loginService.login(userLoginRequest.getUsername(), userLoginRequest.getPassword());
        return Result.ok(token);
    }

    @ApiOperation("获取个人用户信息")
    @GetMapping("info")
    public Result<User> info(){
        LoginUser loginUser = (LoginUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getById(loginUser.getUser().getUserId());
        return Result.ok(user);
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
        // 在加入权限系统后，还要考虑权限的问题，操作权限和数据权限？数据权限是啥
        if(user.getUserId()!=null){
            // 判断当前用户是否已经存在,当前用户昵称是否已经存在,检查电话好嘛是否已经存在
            userService.updateById(user);
        }else{
            // 判断当前用户是否已经存在,当前用户昵称是否已经存在,检查电话好嘛是否已经存在
            userService.save(user);
        }
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
