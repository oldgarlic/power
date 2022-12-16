package com.lll.poweradmin.model.dto;

import com.lll.poweradmin.model.domain.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

/**
 * 登录用户信息
 *
 * @author oldgarlic
 * @since 2022/12/13
 */
@Data
public class LoginUser implements UserDetails {

    private User user;
    private Set<String> permissions;
    private Long loginTime;
    private Long expireTime;
    /**
     * 登录时分配的uuid
     */
    private String loginUserId;
    // 登录信息：登录的ip地址，根据ip地址查询出来的地址，浏览器类型，操作系统
    private String loginIp;
    private String loginLocation;
    private String loginOS;
    private String loginBrowser;

    public LoginUser(User user, Set<String> permissions) {
        this.user = user;
        this.permissions = permissions;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
