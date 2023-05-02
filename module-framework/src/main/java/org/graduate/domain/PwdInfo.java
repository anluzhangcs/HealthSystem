package org.graduate.domain;

import lombok.Data;

/**
 * @author: Zhanghao
 * @date: 2023/5/2-15:07
 * @Description
 */

@Data
public class PwdInfo {

    private String oldPassword;

    private String newPassword;

    private String confirmPassword;

}
