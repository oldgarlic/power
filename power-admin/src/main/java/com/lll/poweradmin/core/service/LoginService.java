package com.lll.poweradmin.core.service;

import com.lll.poweradmin.core.security.AuthenticationContextHolder;
import com.lll.poweradmin.model.dto.LoginUser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LoginService {

    @Resource
    private TokenService tokenService;
    @Resource
    private AuthenticationManager authenticationManager;


    public String login(String loginUsername,String loginPassword){
        // 1. 校对用户名和密码
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUsername, loginPassword);
        AuthenticationContextHolder.setContext(authenticationToken);
        // 调用UserDetailService的认证方法
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        // 需要清楚，要不然会OOS
        AuthenticationContextHolder.clear();
        LoginUser loginUser =  (LoginUser) authentication.getPrincipal();
        // 2. 返回token
        return tokenService.createToken(loginUser);
    }

}
