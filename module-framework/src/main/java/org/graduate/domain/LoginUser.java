package org.graduate.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.graduate.domain.entity.Permission;
import org.graduate.domain.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @author: Zhanghao
 * @date: 2023/4/8-21:47
 * @Description 封装登陆用户信息
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser implements UserDetails {

    private User user;
    private List<Permission> permissionList;
    private Collection<? extends GrantedAuthority> authorities;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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
        return user.getIsAccountNonExpired() == 1 ? true : false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getIsAccountNonLocked() == 1 ? true : false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.getIsCredentialsNonExpired() == 1 ? true : false;
    }

    @Override
    public boolean isEnabled() {
        return user.getIsEnabled() == 1 ? true : false;
    }

}
