package org.graduate.security.filter;

import org.graduate.domain.LoginUser;
import org.graduate.enums.HttpCode;
import org.graduate.exception.SystemException;
import org.graduate.redis.RedisCache;
import org.graduate.utils.JwtUtil;
import org.graduate.utils.ResponseResult;
import org.graduate.utils.WebRenderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author: Zhanghao
 * @date: 2023/4/11-20:42
 * @Description Jwt认证过滤器
 */

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    /**
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //判断请求头中是否有token信息
        String token = request.getHeader("token");
        if (Objects.isNull(token)) {
            //继续判断参数中是否有token
            token = request.getParameter("token");
        }
        if (Objects.isNull(token)) {
            //直接放行,交给SecurityFilterChain处理
            filterChain.doFilter(request, response);
            return;
        }

        if (JwtUtil.checkToken(token)) {

            //获取userId与SecurityContext中的authentication比对
            String id = JwtUtil.getIdByJwtToken(token);
            LoginUser loginUser = redisCache.getCacheObject("login" + id);
            if (Objects.isNull(loginUser)) {
                throw new SystemException(HttpCode.NEED_LOGIN);
            }
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request, response);
        } else {
            //说明token过期/错误,需重新登录
            WebRenderUtil.render(response, ResponseResult.failure(200, "token失效,需重新登录"));
            return;
        }


    }
}
