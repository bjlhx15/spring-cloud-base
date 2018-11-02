package com.lhx.springcloud.security.config;

import com.lhx.springcloud.security.security.UserServiceDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

/**
 * @author lihongxu
 * @desc @link(https://github.com/bjlhx15/spring-cloud-base)
 * @since 2018/10/25 下午5:26
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserServiceDetail userServiceDetail;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
//        http
//                .authorizeRequests().anyRequest().authenticated()
//                .and()
//                .csrf().disable();

        http.csrf().disable();
        http
                .requestMatchers().antMatchers("/oauth/**", "/login/**", "/logout/**", "/uaa/oauth/**")
                .and()
                .authorizeRequests()
                .antMatchers("/oauth/**", "/uaa/oauth/**").authenticated()
                .and()
                .formLogin().permitAll();
        // @formatter:on
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userServiceDetail).passwordEncoder(new BCryptPasswordEncoder());
//        auth.userDetailsService(userServiceDetail).passwordEncoder(NoOpPasswordEncoder.getInstance());

//        auth.inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance())
//                .withUser("admin").password("123456").roles("USER", "ADMIN");
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
