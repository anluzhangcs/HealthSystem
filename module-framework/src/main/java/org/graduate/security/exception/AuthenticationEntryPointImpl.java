package org.graduate.security.exception;

import org.graduate.utils.ResponseResult;
import org.graduate.utils.WebRenderUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: Zhanghao
 * @date: 2023/4/12-17:26
 * @Description 认证异常处理
 */

public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        //
        WebRenderUtil.render(response, ResponseResult.ok().setMessage(authException.getMessage()));
    }
}
