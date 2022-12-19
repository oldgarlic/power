package com.lll.poweradmin.core.handle;

import com.alibaba.fastjson2.JSON;
import com.lll.poweradmin.common.Result;
import com.lll.poweradmin.core.service.TokenService;
import com.lll.poweradmin.model.dto.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    @Resource
    private TokenService tokenService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if(loginUser!=null){
            tokenService.deleteLoginUser(loginUser.getLoginUserId());
            log.info("用户:{} 退出登录成功",loginUser.getUser().getUsername());
        }
        response.setStatus(200);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().print(JSON.toJSONString(Result.ok("用户退出登录成功")));
    }
}
