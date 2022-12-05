package com.lll.poweradmin.model.vo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lll.poweradmin.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserPageRequest extends PageRequest {
    // 需要查询当前部门下的所有部门
    private Long deptId;

}
