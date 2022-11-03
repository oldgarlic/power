package com.lll.poweradmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.core.util.StrUtil;
import com.lll.poweradmin.model.domain.Post;
import com.lll.poweradmin.mapper.PostMapper;
import com.lll.poweradmin.service.IPostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 岗位信息表 服务实现类
 * </p>
 *
 * @author oldgarlic
 * @since 2022-11-03
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements IPostService {
    @Resource
    private PostMapper postMapper;

    /**
     * 分页查询
     *
     * @param post 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    public Page<Post> pageQuery(Post post, long current, long size){
        //1. 构建动态查询条件
        //LambdaQueryWrapper<Post> queryWrapper = new LambdaQueryWrapper<>();
        //if(StrUtil.isNotBlank(post.getPostCode())){
        //    queryWrapper.eq(Post::getPostCode, post.getPostCode());
        //}
        //if(StrUtil.isNotBlank(post.getPostName())){
        //    queryWrapper.eq(Post::getPostName, post.getPostName());
        //}
        //if(StrUtil.isNotBlank(post.getCreateBy())){
        //    queryWrapper.eq(Post::getCreateBy, post.getCreateBy());
        //}
        //if(StrUtil.isNotBlank(post.getUpdateBy())){
        //    queryWrapper.eq(Post::getUpdateBy, post.getUpdateBy());
        //}
        ////2. 执行分页查询
        //Page<Post> pagin = new Page<>(current , size , true);
        //IPage<Post> selectResult = postMapper.selectByPage(pagin , queryWrapper);
        //pagin.setPages(selectResult.getPages());
        //pagin.setTotal(selectResult.getTotal());
        //pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return null;
    }


}
