package org.graduate.controller;

import org.graduate.domain.EmailInfo;
import org.graduate.domain.PwdInfo;
import org.graduate.domain.entity.User;
import org.graduate.service.UserService;
import org.graduate.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

/**
 * @author: Zhanghao
 * @date: 2023/4/8-18:02
 * @Description
 */

@RestController
@RequestMapping("/happyhome/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user) {
        return userService.login(user);
    }

    @GetMapping("/getInfo")
    public ResponseResult getInfo() {
        return userService.getInfo();
    }

    @GetMapping("/getMenuList")
    public ResponseResult getMenuList() {
        return userService.getMenuList();
    }

    @GetMapping("/logout")
    public ResponseResult logout() {
        return userService.logout();
    }

    @PutMapping("/edit/avatar")
    public ResponseResult changeAvatar(@RequestBody String url) {
        return userService.changeAvatar(url);
    }

    @PutMapping("/edit/profile")
    public ResponseResult editProfile(@RequestBody User user) {
        return userService.editProfile(user);
    }

    @PutMapping("/edit/pwd")
    public ResponseResult updatePwd(@RequestBody PwdInfo pwdInfo) {
        return userService.updatePwd(pwdInfo);
    }

    @GetMapping("/email/code")
    public ResponseResult sendCode(String to) throws MessagingException {
        return userService.sendCode(to);
    }

    @GetMapping("/email/bind")
    public ResponseResult bindMail(String to, String code) {
        return userService.bindMail(to, code);
    }

    @PostMapping("/email/login")
    public ResponseResult loginByEamil(@RequestBody EmailInfo emailInfo) {
        return userService.loginByEamil(emailInfo);
    }
}

