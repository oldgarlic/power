package com.lll.poweradmin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lll.poweradmin.model.domain.Dept;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 部门信息表 服务类
 * </p>
 *
 * @author oldgarlic
 * @since 2022-11-03
 */
public interface IDeptService extends IService<Dept> {

    List<Integer> querySubDeptIds(long deptId);
}
