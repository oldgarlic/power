package com.lll.poweradmin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lll.poweradmin.common.Result;
import com.lll.poweradmin.model.domain.Dept;
import com.lll.poweradmin.common.PageRequest;
import com.lll.poweradmin.service.IDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 部门信息表 前端控制器
 * </p>
 *
 * @author oldgarlic
 * @since 2022-11-03
 */
@Api(tags = "部门信息表对象功能接口")
@RestController
@RequestMapping("/dept")
public class DeptController {
    @Resource
    private IDeptService deptService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation("通过ID查询单条数据")
    @GetMapping("{id}")
    public Result<Dept> queryById(@PathVariable("id") long id){
        return Result.ok(deptService.getById(id));
    }

    /**
     * 分页查询
     *
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @ApiOperation("分页查询")
    @PostMapping("page")
    public Result<Page<Dept>> pageQuery(@RequestBody PageRequest pageRequest){
        Page<Dept> deptPage = pageRequest.buildPage(Dept.class);
        Page<Dept> page = deptService.page(deptPage, pageRequest.build(Dept.class));
        return Result.ok(page);
    }

    /**
     * 查询部门列表
     * @return 部门列表对象
     */
    @ApiOperation("部门列表")
    @PostMapping("list")
    public Result<List<Dept>> deptList(@RequestBody PageRequest pageRequest){
        return Result.ok(deptService.list(pageRequest.build(Dept.class)));
    }


    /**
     * 新增数据
     *
     * @param dept 实例对象
     * @return 实例对象
     */
    @ApiOperation("新增数据")
    @PostMapping
    public Result<Boolean> add(@RequestBody Dept dept){
        if(dept.getDeptId()!=null){
            return Result.ok(deptService.updateById(dept));
        }else {
            return Result.ok(deptService.save(dept));
        }
    }

    /**
     * 更新数据
     *
     * @param dept 实例对象
     * @return 实例对象
     */
    @ApiOperation("更新数据")
    @PutMapping
    public Result<Boolean> edit(@RequestBody Dept dept){
        return Result.ok(deptService.updateById(dept));
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @ApiOperation("通过主键删除数据")
    @DeleteMapping("{id}")
    public Result<Boolean> deleteById(@PathVariable("id") long id) {
        return Result.ok(deptService.removeById(id));
    }
}
