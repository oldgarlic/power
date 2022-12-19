package com.lll.poweradmin.core.service;

import com.lll.poweradmin.common.enums.UserStatus;
import com.lll.poweradmin.common.exception.ServiceException;
import com.lll.poweradmin.common.exception.UserException;
import com.lll.poweradmin.model.domain.User;
import com.lll.poweradmin.model.dto.LoginUser;
import com.lll.poweradmin.service.IUserService;
import com.sun.xml.internal.ws.api.ServiceSharedFeatureMarker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;


@Service("userDetailsService")
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService {

    @Resource
    private IUserService userService;

    @Resource
    private PasswordService passwordService;

    @Override
    public UserDetails loadUserByUsername(String username)  {
        User user = userService.selectUserByUsername(username);
        if(Objects.isNull(user)){
            // 如果对象为空
            throw new UserException("登录用户不存在，用户名：" + username);
        }

        if(user.getStatus().equals(UserStatus.DISABLE.getCode())){
            // 如果停用
            throw new UserException("当前用户已停用，用户名："+username);
        }

        if(user.getDelFlag().equals(UserStatus.DELETED.getCode())){
            // 如果已经删除
            throw new UserException("当前用户已删除，用户名："+username);
        }
        // 匹配密码
        passwordService.validate(user);

        return createLoginUser(user);
    }

    private LoginUser createLoginUser(User user){
        // TODO: 这里还需要解析出用户的权限
        return new LoginUser(user,new HashSet<>());
    }



}
