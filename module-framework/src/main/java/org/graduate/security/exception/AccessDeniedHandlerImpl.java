package org.graduate.security.exception;

import org.graduate.utils.ResponseResult;
import org.graduate.utils.WebRenderUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: Zhanghao
 * @date: 2023/4/12-17:31
 * @Description
 */

public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        WebRenderUtil.render(response, ResponseResult.ok().setMessage(accessDeniedException.getMessage()));
    }
}
