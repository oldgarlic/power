package com.lll.poweradmin.core.service;

import com.lll.poweradmin.core.security.UserInfoContextHolder;
import com.lll.poweradmin.model.domain.User;
import jdk.nashorn.internal.parser.Token;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.annotation.Resource;

public class LoginService {

    @Resource
    private TokenService tokenService;
    @Resource
    private AuthenticationManager authenticationManager;


    public String login(String loginUsername,String loginPassword){
        // 0.参数校验
        // TODO
        // 1. 校对用户名和密码
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUsername, loginPassword);
        UserInfoContextHolder.setContext(authenticationToken);
        // 调用UserDetailService的认证方法
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        User user =  (User) authentication.getPrincipal();
        // 2. 返回token
        String token = tokenService.createToken(user);
        //
        System.out.println("TODO");
        return token;
    }
}
