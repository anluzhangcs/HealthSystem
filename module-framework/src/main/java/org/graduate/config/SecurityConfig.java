package org.graduate.config;

import org.graduate.mail.EmailCodeAuthenticationProvider;
import org.graduate.security.UserDetailsServiceimpl;
import org.graduate.security.exception.AccessDeniedHandlerImpl;
import org.graduate.security.exception.AuthenticationEntryPointImpl;
import org.graduate.security.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * @author: Zhanghao
 * @date: 2023/4/8-18:17
 * @Description Spring Security配置类
 */

@Configuration
@EnableWebSecurity
//开启权限配置
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private UserDetailsServiceimpl userDetailsServiceimpl;

    /**
     * 注入自定义邮箱认证provider
     */
    @Bean
    public EmailCodeAuthenticationProvider emailCodeAuthenticationProvider() {
        EmailCodeAuthenticationProvider emailCodeAuthenticationProvider = new EmailCodeAuthenticationProvider();
        return emailCodeAuthenticationProvider;
    }

    /**
     * 用户名密码认证provider
     *
     * @return
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsServiceimpl);
        daoAuthenticationProvider.setUserDetailsPasswordService(userDetailsServiceimpl);
        return daoAuthenticationProvider;
    }

    /**
     * 注入AuthenticationManager
     * <p>
     * //     * @param authenticationConfiguration
     *
     * @return
     * @throws Exception
     */
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(emailCodeAuthenticationProvider());
//        authenticationManagerBuilder.authenticationProvider();
        authenticationManagerBuilder.authenticationProvider(daoAuthenticationProvider());
        return authenticationManagerBuilder.build();
    }


    /**
     * 配置HttpSecurity
     *
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.
                //关闭csrf
                        csrf().disable()
                //关闭session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //开启跨域
                .cors()
                .configurationSource(corsConfigurationSource())
                .and()
                //配置请求认证
                .authorizeRequests()
                .mvcMatchers("/happyhome/user/login").permitAll()
                .mvcMatchers("/happyhome/user/email/login").permitAll()
                .mvcMatchers("/happyhome/user/email/code").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new AuthenticationEntryPointImpl())
                .accessDeniedHandler(new AccessDeniedHandlerImpl());

//        //添加provider
//        http.authenticationProvider(emailCodeAuthenticationProvider());

        //配置Jwt过滤器
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        http.logout().disable();

        return http.build();
    }


    /**
     * 跨域配置
     *
     * @return
     */
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
        corsConfiguration.setAllowedMethods(Arrays.asList("*"));
        corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
        corsConfiguration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
