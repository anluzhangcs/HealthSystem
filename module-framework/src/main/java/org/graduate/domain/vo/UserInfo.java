package org.graduate.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: Zhanghao
 * @date: 2023/4/13-17:52
 * @Description
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo implements Serializable {
    private Long id;//用户ID
    private String username;//用户名
    private String avatar;//头像
    private Object[] roles;//角色权限集合
    //昵称
    private String nickName;
    //电话
    private String phoneNumber;
    //邮箱
    private String email;
}
