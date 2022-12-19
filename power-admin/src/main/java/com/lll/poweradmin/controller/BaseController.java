package com.lll.poweradmin.controller;

import com.lll.poweradmin.common.exception.ServiceException;
import com.lll.poweradmin.common.exception.UserException;
import com.lll.poweradmin.model.dto.LoginUser;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class BaseController {
    protected LoginUser getLoginUser(){
        try {
           return (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }catch(Exception e){
            throw new UserException("获取用户信息异常");
        }
    }
}
