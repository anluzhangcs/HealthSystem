package org.graduate.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author: Zhanghao
 * @date: 2023/5/3-10:07
 * @Description 提供自己的AuthenticationProvider
 */


public class EmailCodeAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * 认证逻辑
     *
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // supports方法通过已经说明token为EmailCodeAuthenticationToken，因此可以强转
        EmailCodeAuthenticationToken authenticationToken = (EmailCodeAuthenticationToken) authentication;
        // 此时principal为email，调用（自定义）UserDetailsService，通过email获取UserDetails
        UserDetails loginUser = userDetailsService.loadUserByUsername((String) authenticationToken.getPrincipal());

        if (loginUser == null) {
            // 如果查找不到数据，抛出内部服务异常
            // 这个InternalAuthenticationServiceException异常将被视为可处理异常，不会被最终抛出
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }

        // 重新生成（已认证）Token
        EmailCodeAuthenticationToken authenticationResult = new EmailCodeAuthenticationToken(loginUser.getAuthorities(), loginUser);
        // 将（未认证）Token中的IP、session等信息放入（已认证）Token中
        authenticationResult.setDetails(authenticationToken.getDetails());

        return authenticationResult;
    }

    /**
     * 判断是否当前认证请求是否是EmailCodeAuthenticationToken
     *
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return EmailCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
