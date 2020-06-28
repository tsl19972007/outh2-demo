package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * @author ：tsl
 * @date ：Created in 2020/6/18 12:55
 * @description：spring security 配置
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)//启用注解
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 配置内存用户，实际使用时使用数据库查询用户
     *
     * @return
     */
    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("admin").password(PasswordEncoderFactories.
                createDelegatingPasswordEncoder().encode("admin")).authorities("USER").build());
        return manager;
    }

    /**
     * 配置AuthenticationManager，配置授权服务器时使用
     *
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }

    /**
     * 除了/oauth/token放行，其他路径全部拦截
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //SpringSecurity默认开启csrf防御，发起Patch、Post、Put、Delete请求会返回403
        //此处禁用默认的csrf防御
        http.csrf().disable();

        http.requestMatchers()//使HttpSecurity接收以"/login/","/oauth/"开头请求。
                .antMatchers("/oauth/**", "/principal", "/login/**", "/logout/**")
                .and()
                .authorizeRequests()
                .antMatchers("/oauth/**", "/principal").authenticated()//用户需要登陆后才能授权
                .and()
                .formLogin();//开启表单登陆
    }
}
