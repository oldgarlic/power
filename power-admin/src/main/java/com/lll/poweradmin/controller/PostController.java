package com.lll.poweradmin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lll.poweradmin.common.Result;
import com.lll.poweradmin.model.domain.Post;
import com.lll.poweradmin.common.PageRequest;
import com.lll.poweradmin.service.IPostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 岗位信息表 前端控制器
 * </p>
 *
 * @author oldgarlic
 * @since 2022-11-03
 */
@Api(tags = "岗位信息表对象功能接口")
@RestController
@RequestMapping("/post")
public class PostController {
    @Resource
    private IPostService postService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation("通过ID查询单条数据")
    @GetMapping("{id}")
    public Result<Post> queryById(@PathVariable("id") long id){
        return Result.ok(postService.getById(id));
    }

    /**
     * 分页查询
     *
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @ApiOperation("分页查询")
    @PostMapping("page")
    public Result<Page<Post>> pageQuery(@RequestBody PageRequest pageRequest){
        Page<Post> postPage = new Page<>(pageRequest.getPageNumber(),pageRequest.getPageSize());
        Page<Post> page = postService.page(postPage, pageRequest.build(Post.class));
        return Result.ok(page);
    }

    /**
     * 新增数据
     *
     * @param post 实例对象
     * @return 实例对象
     */
    @ApiOperation("新增数据")
    @PostMapping
    public Result<Boolean> add(@RequestBody Post post){
        return Result.ok(postService.save(post));
    }

    /**
     * 更新数据
     *
     * @param post 实例对象
     * @return 实例对象
     */
    @ApiOperation("更新数据")
    @PutMapping
    public Result<Boolean> edit(@RequestBody Post post){
        return Result.ok(postService.updateById(post));
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
        return Result.ok(postService.removeById(id));
    }
}
