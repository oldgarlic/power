package com.lll.poweradmin.common;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserPageRequest extends PageRequest{
    private String deptId;

    @Override
    public <T> QueryWrapper<T> build(Class<T> t) {
        QueryWrapper<T> queryWrapper = super.build(t);
        // 暂时不做实现吧
        return queryWrapper;
    }
}
