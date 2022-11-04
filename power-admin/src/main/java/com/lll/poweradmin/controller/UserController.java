package com.lll.poweradmin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lll.poweradmin.common.Result;
import com.lll.poweradmin.model.domain.User;
import com.lll.poweradmin.common.PageRequest;
import com.lll.poweradmin.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
     * @param pageRequest 查询条件
     * @return 分页结果
     */
    @ApiOperation("分页查询")
    @PostMapping("page")
    public Result<Page<User>> pageQuery(@RequestBody PageRequest pageRequest){
        Page<User> postPage = new Page<>(pageRequest.getPageNumber(),pageRequest.getPageSize());
        Page<User> page = userService.page(postPage, pageRequest.build(User.class));
        return Result.ok(page);
    }

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @ApiOperation("新增数据")
    @PostMapping
    public Result<Boolean> add(@RequestBody User user){
        // 这里需要增加维护关系
        return Result.ok(userService.save(user));
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
     * @param id 主键
     * @return 是否成功
     */
    @ApiOperation("通过主键删除数据")
    @DeleteMapping("{id}")
    public Result<Boolean> deleteById(@PathVariable("id") long id){
        // 这里改成逻辑删除
        return Result.ok(userService.removeById(id));
    }
}
