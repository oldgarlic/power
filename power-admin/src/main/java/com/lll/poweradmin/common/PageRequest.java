package com.lll.poweradmin.common;

import lombok.Data;

import java.util.HashMap;

@Data
public class PageRequest {
    /**
     * 页数
     */
    private long pageNumber;
    /**
     * 页数量
     */
    private long pageSize;
    /**
     * 排序字段
     */
    private String sort;
    /**
     * 升序/降序
     */
    private String order;
    /**
     * 过滤条件
     */
    private HashMap<String,String> filterMap;
}
