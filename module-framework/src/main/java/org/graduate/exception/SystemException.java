package org.graduate.exception;

import org.graduate.enums.HttpCode;

/**
 * @author: Zhanghao
 * @date: 2023/4/13-17:16
 * @Description
 */


public class SystemException extends RuntimeException {

    private int code;

    private String msg;

    public SystemException(HttpCode httpCodeEnum) {
        super(httpCodeEnum.getMsg());
        this.code = httpCodeEnum.getCode();
        this.msg = httpCodeEnum.getMsg();
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
