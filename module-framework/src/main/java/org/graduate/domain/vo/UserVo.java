package org.graduate.domain.vo;

import lombok.Data;

/**
 * @author: Zhanghao
 * @date: 2023/4/10-13:33
 * @Description
 */
@Data
public class UserVo {

    private String avatar;//头像
    //昵称
    private String nickName;
    //电话
    private String phoneNumber;
    //邮箱
    private String email;
}
