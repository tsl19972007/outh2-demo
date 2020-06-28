package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

/**
 * @author ：tsl
 * @date ：Created in 2020/6/18 15:33
 * @description：资源服务器配置
 */

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.
                anonymous().disable()
                .requestMatchers().antMatchers("/test", "/testPost", "/test2", "/principal")
                .and().authorizeRequests()
                .antMatchers("/test", "/testPost", "/principal").permitAll()//这两个接口用户授权后可以访问
                .antMatchers("/test2").denyAll()//这个接口用户授权后仍然拒绝
                .and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
    }
}
