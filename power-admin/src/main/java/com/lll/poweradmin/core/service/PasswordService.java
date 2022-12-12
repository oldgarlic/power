package com.lll.poweradmin.core.service;

import com.lll.poweradmin.common.exception.ServiceException;
import com.lll.poweradmin.common.exception.UserException;
import com.lll.poweradmin.model.domain.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 密码验证服务
 *
 * @author oldgarlic
 * @since 2022/12/11
 */
public class PasswordService {

    public void validate(User user){
        // TODO: 1、密码重试; 2、密码如果重试次数过多，会锁住
        // 如果密码认证失败，就报错，认证成功就成功
        String password = "";
        if(!matchPassword(user.getPassword(),password)){
            throw new UserException("密码不正确");
        }
    }

    /**
     *
     * @param loginPassword   登录密码
     * @param encodedPassword 加密后的密码
     * @return 密码是否匹配
     */
    public boolean matchPassword(String loginPassword,String encodedPassword){
          //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
          //return passwordEncoder.matches(loginPassword, encodedPassword);
        return loginPassword.equals(encodedPassword);
    }
}
