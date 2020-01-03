package com.puzek.platform.inspection.security;

import com.cwp.cloud.api.UserApi;
import com.cwp.cloud.bean.user.User;
import com.puzek.platform.inspection.entity.Operator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserServiceImpl implements UserDetailsService {
    final UserApi userApi;

    @Autowired
    public MyUserServiceImpl(UserApi userApi) {
        this.userApi = userApi;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userApi.login(name);
        if (user == null) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        Operator operator = new Operator();
        operator.setId(user.getId());
        operator.setName(user.getName());
        operator.setPassword(user.getPassword());
        operator.setArea(user.getArea());
        operator.setPhoneNumber(user.getPhoneNumber());
        operator.setUsername(name);
        operator.setRoles(user.getRoles());
        operator.setWorkStatus(user.getWorkStatus());
        return operator;
    }
}
