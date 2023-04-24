package org.graduate.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * (User)
 *
 * @author Zhanghao
 * @since 2023-04-14 23:01:39
 */
@Data
@NoArgsConstructor
public class User {
    //用户id
    private Long id;
    //登录名称(用户名)
    private String username;
    //登录密码
    private String password;
    //用户头像
    private String avatar;
    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    //修改时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    //帐户是否过期(1-未过期，0-已过期)
    private Integer isAccountNonExpired;
    //帐户是否被锁定(1-未过期，0-已过期)
    private Integer isAccountNonLocked;
    //密码是否过期(1-未过期，0-已过期)
    private Integer isCredentialsNonExpired;
    //帐户是否可用(1-可用，0-禁用)
    private Integer isEnabled;
    //昵称
    private String nickName;
    //电话
    private String phoneNumber;
    //邮箱
    private String email;
    //是否删除(0-未删除，1-已删除)
    private Integer delFlag;
    //角色id
    private Long roleId;

}

