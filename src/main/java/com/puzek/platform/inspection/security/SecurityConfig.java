package com.puzek.platform.inspection.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // 开启注解拦截
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AjaxAuthenticationEntryPoint authenticationEntryPoint;  //  未登陆时返回 JSON 格式的数据给前端（否则为 html）

    @Autowired
    AjaxAuthenticationSuccessHandler authenticationSuccessHandler;  // 登录成功返回的 JSON 格式数据给前端（否则为 html）

    @Autowired
    AjaxAuthenticationFailureHandler authenticationFailureHandler;  //  登录失败返回的 JSON 格式数据给前端（否则为 html）

    @Autowired
    AjaxLogoutSuccessHandler logoutSuccessHandler;  // 注销成功返回的 JSON 格式数据给前端（否则为 登录时的 html）

    @Autowired
    AjaxAccessDeniedHandler accessDeniedHandler;    // 无权访问返回的 JSON 格式数据给前端（否则为 403 html 页面）

    @Autowired
    AjaxAuthenticationTimeOut sessionTimeOut;     // session 超时返回的json格式数据给前端

    @Autowired
    MyUserServiceImpl myUserService;

    @Autowired
    private DataSource dataSource;
    @Value("${customize.whitelist2}")
    private String whitelist;

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        //自动创建数据库表，使用一次后注释掉，不然会报错
        // 或者执行以下sql
        // create table persistent_logins (username varchar(64) not null, series varchar(64) primary key, token varchar(64) not null, last_used timestamp not null);
        //jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }

    @Bean // 自定义安全认证
    public LoginAuthenticationProvider authenticationProvider() {
        LoginAuthenticationProvider provider = new LoginAuthenticationProvider();
        provider.setHideUserNotFoundExceptions(false);
        provider.setUserDetailsService(myUserService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // 加入自定义的安全认证
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).and()
                .csrf().disable() // 跨域资源共享限制.无效
                .cors().disable() // 跨域伪造请求限制.无效
                .authorizeRequests()
                .antMatchers("/deploy/**").permitAll()
                // 所有用户均可访问的资源
                .anyRequest().authenticated()// 其他 url 需要身份认证
                .and().formLogin().loginProcessingUrl("/op/login")
                .usernameParameter("name")
                .passwordParameter("pwd")
                .successHandler(authenticationSuccessHandler) // 登录成功
                .failureHandler(authenticationFailureHandler) // 登录失败
                .permitAll()
                .and()   // 这里设置了记住登录的话，就算是登录超时了，也会自动调用登录的，前端没有感觉到超时
                .rememberMe()
                .rememberMeParameter("rememberMe")
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(30 * 24 * 60 * 60)
                .userDetailsService(myUserService)
                .and()
                .logout()
                .logoutUrl("/op/logout")
                .logoutSuccessHandler(logoutSuccessHandler)
                .permitAll().and().sessionManagement().invalidSessionStrategy(sessionTimeOut).and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler); // 无权访问
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        String[] whiteListArray = whitelist.split(",");
        System.out.println("whitelist:" + whitelist);
        String[] array = {"/dist/**", "/static/**", "**/css/**", "**/js/**", "**/img/**", "**/fonts/**", "**/favicon.ico", "/index.html",};
        List<String> list = new ArrayList<>();
        Collections.addAll(list, whiteListArray);
        Collections.addAll(list, array);
        web.ignoring().antMatchers(list.toArray(new String[list.size()]));
    }

    // 解决 //aa/xxx /aa/xxx 多个横杠的问题
    @Bean
    public HttpFirewall httpFirewall() {
        return new DefaultHttpFirewall();
    }
}
