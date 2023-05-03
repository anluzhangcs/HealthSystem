package org.graduate.mail;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.util.Collection;

/**
 * @author: Zhanghao
 * @date: 2023/5/3-9:37
 * @Description 仿照UsernamePasswordAuthenticationToken写的mail token
 */

public class EmailCodeAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    /**
     * 主体:
     * 登录前-邮箱地址
     * 登陆后-UserDetail
     */
    private final Object principal;

    /**
     * @param principal
     */
    public EmailCodeAuthenticationToken(Object principal) {
        super(null);
        this.principal = principal;
        setAuthenticated(false);
    }

    /**
     * Creates a token with the supplied array of authorities.
     *
     * @param authorities the collection of <tt>GrantedAuthority</tt>s for the principal
     *                    represented by this authentication object.
     * @param principal
     */
    public EmailCodeAuthenticationToken(Collection<? extends GrantedAuthority> authorities, Object principal) {
        super(authorities);
        this.principal = principal;
        setAuthenticated(true);
    }

    /**
     * 返回一个为认证token
     *
     * @param principal
     * @return
     */
    public static EmailCodeAuthenticationToken unauthenticated(Object principal) {
        return new EmailCodeAuthenticationToken(principal);
    }

    /**
     * 返回一个认证token
     *
     * @param authorities
     * @param principal
     * @return
     */
    public static EmailCodeAuthenticationToken authenticated(Collection<? extends GrantedAuthority> authorities, Object principal) {
        return new EmailCodeAuthenticationToken(authorities, principal);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

//    @Override
//    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
//        Assert.isTrue(!isAuthenticated,
//                "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
//        super.setAuthenticated(false);
//    }
}
