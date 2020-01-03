package com.puzek.platform.inspection.service;

import com.puzek.platform.inspection.common.Msg;

public interface UserService {
    Msg getUserList(String username, String phoneNumber, int numPage, int rows);

    Msg getUserList(String username, String password, String name, String phoneNumber, int rolesId, String area);

    Msg checkIn();

    Msg checkOut();

    Msg getUserInfo();
}
