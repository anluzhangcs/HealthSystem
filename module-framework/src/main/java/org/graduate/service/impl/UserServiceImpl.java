package org.graduate.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.graduate.domain.LoginUser;
import org.graduate.domain.PwdInfo;
import org.graduate.domain.entity.Permission;
import org.graduate.domain.entity.User;
import org.graduate.domain.vo.RouterVo;
import org.graduate.domain.vo.UserInfo;
import org.graduate.domain.vo.UserVo;
import org.graduate.enums.HttpCode;
import org.graduate.exception.SystemException;
import org.graduate.mail.MailService;
import org.graduate.mail.verify.VerificationCodeService;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
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

    @Autowired
    private VerificationCodeService codeService;

    @Autowired
    private MailService mailService;


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
        LoginUser loginUser = SecurityUtils.getLoginUser();
        User user = loginUser.getUser();
        user.setAvatar(url);
        updateById(user);

        //更新redis
        loginUser.setUser(user);
        redisCache.deleteObject("login" + user.getId());
        redisCache.setCacheObject("login" + user.getId(), loginUser);
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
        //
        LoginUser loginUser = SecurityUtils.getLoginUser();
        User oldUser = loginUser.getUser();
        oldUser.setNickName(user.getNickName());
        updateById(oldUser);
        //更新redis
        loginUser.setUser(oldUser);
        redisCache.deleteObject("login" + oldUser.getId());
        redisCache.setCacheObject("login" + oldUser.getId(), loginUser);

        return ResponseResult.ok().setMessage("更新成功");
    }

    /**
     * 修改密码
     *
     * @param pwdInfo
     * @return
     */
    @Override
    public ResponseResult updatePwd(PwdInfo pwdInfo) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        LoginUser loginUser = SecurityUtils.getLoginUser();
        User user = loginUser.getUser();
        String oldPassword = pwdInfo.getOldPassword();
        String password = user.getPassword();
        //{bcrypt}
        String substring = password.substring(password.indexOf("}") + 1);
        if (passwordEncoder.matches(oldPassword, substring)) {
            String encode = passwordEncoder.encode(pwdInfo.getNewPassword());
            user.setPassword("{bcrypt}" + encode);
            updateById(user);
        } else {
            throw new SystemException(HttpCode.BADPASSWORD);
        }

        //更新redis
        loginUser.setUser(user);
        redisCache.deleteObject("login" + user.getId());
        redisCache.setCacheObject("login" + user.getId(), loginUser);
        return ResponseResult.ok().setMessage("更新成功");
    }

    /**
     * 生成邮箱验证码
     *
     * @param to 接受邮箱
     * @return
     */
    @Override
    public ResponseResult sendCode(String to) throws MessagingException {
        //生成验证码
        String code = codeService.generateVerificationCode();
        //发送邮件
        mailService.sendHtmlMail(to, "欢迎来到幸福敬老院", code);
        //将code保存到redis
        String oldCode = redisCache.getCacheObject("bindCode");
        if (Objects.nonNull(oldCode)) {
            redisCache.deleteObject("bindCode");
        }
        redisCache.setCacheObject("bindCode", code);
        //5分钟失效
        redisCache.expire("bindCode", 5, TimeUnit.MINUTES);
        return ResponseResult.ok().setMessage("发送成功");
    }

    /**
     * 绑定邮箱
     *
     * @param to
     * @param code
     * @return
     */
    @Override
    public ResponseResult bindMail(String to, String code) {
        //从redis中获取验证码
        String serverCode = redisCache.getCacheObject("bindCode");

        //
        LoginUser loginUser = SecurityUtils.getLoginUser();
        User user = loginUser.getUser();
        if (Objects.nonNull(serverCode) && serverCode.equals(code)) {
            //设置邮箱
            user.setEmail(to);
            updateById(user);
            //更新redis中的loginUser
            loginUser.setUser(user);
            redisCache.deleteObject("login" + user.getId());
            redisCache.setCacheObject("login" + user.getId(), loginUser);
        }
        return ResponseResult.ok().setMessage("绑定成功");
    }
}

