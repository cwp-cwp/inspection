package com.puzek.platform.inspection.serviceImpl;

import com.cwp.cloud.api.UserApi;
import com.cwp.cloud.bean.user.User;
import com.github.pagehelper.PageInfo;
import com.puzek.platform.inspection.common.Msg;
import com.puzek.platform.inspection.service.UserService;
import com.puzek.platform.inspection.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserApi userApi;

    @Autowired
    public UserServiceImpl(UserApi userApi) {
        this.userApi = userApi;
    }

    @Override
    public Msg getUserList(String username, String phoneNumber, int numPage, int rows) {
        PageInfo<User> pageInfo = userApi.getUserList(username, phoneNumber, numPage, rows);
        if (pageInfo == null) {
            return Msg.failed("api返回null");
        }
        return Msg.success().add("data", pageInfo);
    }

    @Override
    public Msg getUserList(String username, String password, String name, String phoneNumber, int rolesId, String area) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setName(name);
        user.setPhoneNumber(phoneNumber);
        user.setRolesId(rolesId);
        user.setArea(area);
        user.setAddTime(DateUtil.getFormatDate());
        boolean result = userApi.addUser(user);
        if (result) {
            return Msg.success("添加成功");
        } else {
            return Msg.failed("添加失败");
        }
    }

    @Override
    public Msg checkIn() {
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userApi.login(currentUserName);
        if (user == null) {
            return Msg.failed("请重新登录");
        }
        boolean result = userApi.checkIn(user.getId());
        if (result) {
            return Msg.success("签到成功");
        } else {
            return Msg.failed("签到失败");
        }
    }

    @Override
    public Msg checkOut() {
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userApi.login(currentUserName);
        if (user == null) {
            return Msg.failed("请重新登录");
        }
        boolean result = userApi.checkOut(user.getId());
        if (result) {
            return Msg.success("签退成功");
        } else {
            return Msg.failed("签退失败");
        }
    }

    @Override
    public Msg getUserInfo() {
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userApi.login(currentUserName);
        if(user != null) {
            user.setPhoneNumber("");
        }
        return Msg.success().add("data", user);
    }
}
