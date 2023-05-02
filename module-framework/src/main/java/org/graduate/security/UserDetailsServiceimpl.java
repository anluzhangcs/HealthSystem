package org.graduate.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.graduate.domain.LoginUser;
import org.graduate.domain.entity.Permission;
import org.graduate.domain.entity.User;
import org.graduate.mapper.PermissionMapper;
import org.graduate.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: Zhanghao
 * @date: 2023/4/8-18:16
 * @Description
 */
@Service()
public class UserDetailsServiceimpl implements UserDetailsService, UserDetailsPasswordService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    /**
     * 只完成根据用户名查询用户,后面的密码校验有SpringBoot自动完成
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //1.根据用户名查询user
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        User user = userMapper.selectOne(queryWrapper);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("用户不存在");
        }

        //2.封装权限信息
        List<Permission> permissionList = permissionMapper.getPermissionList(user.getRoleId());
        //获取权限编码
        Set<String> collect = permissionList.stream()
                .filter(Objects::nonNull)
                .map(Permission::getCode)
                .collect(Collectors.toSet());

        String[] strings = collect.toArray(new String[collect.size()]);

        //设置权限列表
        List<GrantedAuthority> authorityList =
                AuthorityUtils.createAuthorityList(strings);

        return new LoginUser(user, permissionList, authorityList);
    }

    /**
     * 实现UserDetailsPasswordService接口,PasswordEncoder升级时更新密码
     *
     * @param user        认证成功后的UserDetails
     * @param newPassword
     * @return 更新后返回新的UserDetails
     */
    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        //强制转换
        LoginUser loginUser = (LoginUser) user;
        User newUser = loginUser.getUser();
        newUser.setPassword(newPassword);
        //更新数据库
        userMapper.updateById(newUser);

        //更新UserDetails
        loginUser.setUser(newUser);
        return loginUser;
    }
}
