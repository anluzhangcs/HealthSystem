package org.graduate.controller;

import org.graduate.domain.entity.User;
import org.graduate.service.UserService;
import org.graduate.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
