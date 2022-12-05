package com.lll.poweradmin.mapper;

import com.lll.poweradmin.model.domain.Dept;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 部门信息表 Mapper 接口
 * </p>
 *
 * @author oldgarlic
 * @since 2022-11-03
 */
public interface DeptMapper extends BaseMapper<Dept> {

    @Select("SELECT b.dept_id,b.parent_id,b.dept_name FROM dept a INNER join dept b on a.dept_id = #{deptId} and  (FIND_IN_SET(a.dept_id,b.ancestors) or b.dept_id = #{deptId});")
    List<Integer> querySubDeptIds(long deptId);
}
