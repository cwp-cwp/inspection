package com.puzek.platform.inspection.service;

import com.puzek.platform.inspection.common.Msg;

public interface RolesService {
    Msg getAllRoles();

    Msg getRolesList(String rolesName, int page, int pageSize);

    Msg addRoles(String rolesName);
}
