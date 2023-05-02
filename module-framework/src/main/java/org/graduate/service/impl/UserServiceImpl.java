package org.graduate.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.graduate.domain.LoginUser;
import org.graduate.domain.entity.Permission;
import org.graduate.domain.entity.User;
import org.graduate.domain.vo.RouterVo;
import org.graduate.domain.vo.UserInfo;
import org.graduate.domain.vo.UserVo;
import org.graduate.mapper.PermissionMapper;
import org.graduate.mapper.UserMapper;
import org.graduate.redis.RedisCache;
import org.graduate.security.UserDetailsServiceimpl;
import org.graduate.service.UserService;
import org.graduate.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * (User)
 *
 * @author Zhanghao
 * @since 2023-04-08 18:01:31
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PermissionMapper permissionMapper;


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceimpl userDetailsServiceimpl;


    /**
     * 登录逻辑
     *
     * @param user
     * @return
     */
    @Override
    public ResponseResult login(User user) {
        //1.封装认证信息
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

        //2.调用AuthenticationManager进行认证
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        //判断是否认证通过
        if (Objects.isNull(authentication)) {
            throw new BadCredentialsException("密码错误");
        }

        //获取UserDetails信息
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        User userInfo = loginUser.getUser();


        //将UserDetails存入到redis中
        redisCache.setCacheObject("login" + userInfo.getId(), loginUser);

        //根据用户id生成token
        String token = JwtUtil.getJwtToken(userInfo.getId().toString(), userInfo.getUsername());

        //封装返回信息
        Map<String, Object> map = new HashMap<>();
        UserVo userVo = BeanCopyUtil.copyBean(userInfo, UserVo.class);
        map.put("token", token);
        ResponseResult result = ResponseResult.ok(map);

        return result;
    }

    /**
     * 退出
     *
     * @return
     */
    @Override
    public ResponseResult logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityContextHolder.clearContext();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userid = loginUser.getUser().getId();
        redisCache.deleteObject("login" + userid);
        return ResponseResult.ok().setMessage("退出成功").setData(loginUser.getUsername());
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    @Override
    public ResponseResult getInfo() {

        //从SecurityContext中获取用户
        User user = SecurityUtils.getLoginUser().getUser();
        //获取新的loginUser
        LoginUser loginUser = (LoginUser) userDetailsServiceimpl.loadUserByUsername(user.getUsername());
        //更新redis
        redisCache.deleteObject("login" + user.getId());
        redisCache.setCacheObject("login" + user.getId(), loginUser);

        //获取所对应的权限信息
        List<Permission> permissionList = loginUser.getPermissionList();
        Object[] roles = permissionList.stream()
                .filter(obj -> Objects.nonNull(obj))
                .map(Permission::getCode)
                .collect(Collectors.toSet())
                .toArray();
        UserInfo userInfo = BeanCopyUtil.copyBean(loginUser.getUser(), UserInfo.class);
        userInfo.setRoles(roles);
        return ResponseResult.ok(userInfo);

    }

    /**
     * 获取菜单信息
     *
     * @return
     */
    @Override
    public ResponseResult getMenuList() {
        //1.从SecurityContext中获取UserDetails
        LoginUser loginUser = SecurityUtils.getLoginUser();

        //2.获取所对应的权限信息
        List<Permission> permissionList = loginUser.getPermissionList();

        //筛选目录和菜单
        List<Permission> collect = permissionList.stream()
                .filter(item -> item != null && item.getType() != 2)
                .collect(Collectors.toList());
//生成路由数据
        List<RouterVo> routerVoList = MenuTree.makeRouter(collect, 0L);

        return ResponseResult.ok(routerVoList);
    }

    /**
     * 改变头像
     *
     * @param url
     * @return
     */
    @Override
    public ResponseResult changeAvatar(String url) {
        User user = SecurityUtils.getLoginUser().getUser();
        user.setAvatar(url);
        updateById(user);
        return ResponseResult.ok();
    }

    /**
     * 更新基本信息
     *
     * @param user
     * @return
     */
    @Override
    public ResponseResult editProfile(User user) {
        //获取authentication
        LoginUser loginUser = SecurityUtils.getLoginUser();
        User oldUser = loginUser.getUser();
        oldUser.setNickName(user.getNickName());
        updateById(oldUser);
        loginUser.setUser(oldUser);
        UserVo userVo = BeanCopyUtil.copyBean(oldUser, UserVo.class);
        return ResponseResult.ok(userVo).setMessage("更新成功");
    }
}

