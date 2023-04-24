package org.graduate.domain.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author: Zhanghao
 * @date: 2023/4/10-13:33
 * @Description
 */
@Data
public class UserVo {

    //用户id
    private Long id;
    //用户名
    private String username;
    //用户年龄
    private Integer age;
    //用户图像
    private String avatar;
    //创建时间
    private LocalDateTime createTime;
    //更新时间
    private LocalDateTime updateTime;
}
