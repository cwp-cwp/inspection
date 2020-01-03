package com.puzek.platform.inspection.controller;

import com.puzek.platform.inspection.common.Msg;
import com.puzek.platform.inspection.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/getUserList")
    @ResponseBody
    public Msg getUserList(String username, String phoneNumber, int page, int pageSize) {
        return userService.getUserList(username, phoneNumber, page, pageSize);
    }

    @RequestMapping("/addUser")
    @ResponseBody
    public Msg addUser(String username, String password, String name, String phoneNumber, int rolesId, String area) {
        return userService.getUserList(username, password, name, phoneNumber, rolesId, area);
    }

    @RequestMapping("/checkIn")
    @ResponseBody
    public Msg checkIn() {
        return userService.checkIn();
    }

    @RequestMapping("/checkOut")
    @ResponseBody
    public Msg checkOut() {
        return userService.checkOut();
    }

    @RequestMapping("/getUserInfo")
    @ResponseBody
    public Msg getUserInfo() {
        return userService.getUserInfo();
    }
}
