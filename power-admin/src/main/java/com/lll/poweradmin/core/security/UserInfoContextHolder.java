package com.lll.poweradmin.core.security;

import org.springframework.security.core.Authentication;

/**
 * 用户身份信息存储
 *
 * @author oldgarlic
 * @since 2022/12/12
 */
public class UserInfoContextHolder {
    private static final ThreadLocal<Authentication> contextHolder = new ThreadLocal<>();

    public static void setContext(Authentication authentication){
        contextHolder.set(authentication);
    }

    public static Authentication getContext(){
        return contextHolder.get();
    }

    public static void clear(){
        contextHolder.remove();
    }
}
