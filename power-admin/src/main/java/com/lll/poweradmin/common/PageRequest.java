package com.lll.poweradmin.common;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

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
    private HashMap<String,String> filter;

    public <T> QueryWrapper<T> build(Class<T> t){
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderBy(true, asc,this.getSort());
        if(this.getFilter()!=null){
            for (Map.Entry<String, String> entry : this.getFilter().entrySet()) {
                String underline = underline(entry.getKey());
                queryWrapper.like(underline,entry.getValue());
            }
        }
        return queryWrapper;
    }

    private String underline(String name) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < name.length(); ++i) {
            char ch = name.charAt(i);
            if (ch >= 'A' && ch <= 'Z') {
                char ch_case = (char) (ch + 32);
                if (i > 0) {
                    buf.append('_');
                }
                buf.append(ch_case);
            } else {
                buf.append(ch);
            }
        }
        return buf.toString();
    }

    public <T> Page<T> buildPage(Class<T> t){
        return new Page<T>(getPageNumber(),getPageSize());
    }

}
