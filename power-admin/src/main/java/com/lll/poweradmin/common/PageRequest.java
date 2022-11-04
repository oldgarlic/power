package com.lll.poweradmin.common;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lll.poweradmin.model.domain.Dept;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;

@Data
public class PageRequest {
    /**
     * 页数
     */
    private long pageNumber = 1;
    /**
     * 页数量
     */
    private long pageSize = 10;
    /**
     * 排序字段
     */
    private String sort = "id";
    /**
     * 升序/降序
     */
    private boolean asc = true;
    /**
     * 过滤条件
     */
    private HashMap<String,String> filterMap;

    public <T> QueryWrapper<T> build(Class<T> t){
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderBy(true, asc,this.getSort());
        if(this.getFilterMap()!=null){
            this.getFilterMap().forEach(queryWrapper::like);
        }
        return queryWrapper;
    }

}
