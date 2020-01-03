package com.puzek.platform.inspection.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class LoginAuthenticationProvider extends DaoAuthenticationProvider {

    @Autowired
    private MyUserServiceImpl myUserService;
    @Autowired
    private void setJdbcUserDetailsService() {
        setUserDetailsService(myUserService);
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        String password = (String) authentication.getCredentials(); // 这个是表单中输入的密码
        if (!userDetails.getPassword().equals(password)) {
            throw new BadCredentialsException("用户名或密码不正确");
        }
    }
}
