package com.lhx.springcloud.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

/**
 * @author lihongxu
 * @desc @link(https://github.com/bjlhx15/spring-cloud-base)
 * ClientDetailsServiceConfigurer：
 * 用来配置客户端详情服务（ClientDetailsService），客户端详情信息在这里进行初始化，你能够把客户端详情信息写死在这里或者是通过数据库来存储调取详情信息。
 * AuthorizationServerSecurityConfigurer：
 * 用来配置令牌端点(Token Endpoint)的安全约束.
 * AuthorizationServerEndpointsConfigurer：
 * 用来配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)。
 * @since 2018/10/22 下午5:36
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    // 为了/oauth/check_token 可以使用
    //相当于：pre认证
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer
                .tokenKeyAccess("permitAll()") //url:/oauth/token_key,exposes public key for token verification if using JWT tokens
                .checkTokenAccess("isAuthenticated()") //url:/oauth/check_token allow check token
                .allowFormAuthenticationForClients();
    }
    // 客户端凭证
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory() // 使用in-memory存储
                .withClient("client") // client_id
//                .secret("secret") // client_secret
                .secret("{noop}secret")
                .authorizedGrantTypes("authorization_code","client_credentials", "password", "refresh_token") // 该client允许的授权类型
                .scopes("all")// 允许的授权范围
        ;
    }
//    @Autowired
//    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;

    //要使用refresh_token的话，需要额外配置userDetailsService
    //相当于：after认证
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //这里额外指定了/oauth/token的password模式要使用的userDetailsService
        endpoints.authenticationManager(authenticationManager);
//        endpoints.userDetailsService(userDetailsService);
    }
}
