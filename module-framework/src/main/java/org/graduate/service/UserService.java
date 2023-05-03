package org.graduate.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.graduate.domain.PwdInfo;
import org.graduate.domain.entity.User;
import org.graduate.utils.ResponseResult;

import javax.mail.MessagingException;

/**
 * (User)
 *
 * @author Zhanghao
 * @since 2023-04-08 18:01:31
 */
public interface UserService extends IService<User> {


    ResponseResult login(User user);

    ResponseResult logout();

    ResponseResult getInfo();

    ResponseResult getMenuList();

    ResponseResult changeAvatar(String url);

    ResponseResult editProfile(User user);

    ResponseResult updatePwd(PwdInfo pwdInfo);

    ResponseResult sendCode(String to) throws MessagingException;

    ResponseResult bindMail(String to, String code);
}

