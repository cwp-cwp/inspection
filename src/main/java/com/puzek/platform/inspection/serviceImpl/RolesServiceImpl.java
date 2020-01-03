package com.puzek.platform.inspection.serviceImpl;

import com.cwp.cloud.api.UserApi;
import com.cwp.cloud.bean.user.Roles;
import com.github.pagehelper.PageInfo;
import com.puzek.platform.inspection.common.Msg;
import com.puzek.platform.inspection.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolesServiceImpl implements RolesService {
    private final UserApi userApi;

    @Autowired
    public RolesServiceImpl(UserApi userApi) {
        this.userApi = userApi;
    }

    @Override
    public Msg getAllRoles() {
        return Msg.success().add("data", userApi.getAllRolesList());
    }

    @Override
    public Msg getRolesList(String rolesName, int page, int pageSize) {
        PageInfo<Roles> pageInfo = userApi.getRolesList(rolesName, page, pageSize);
        if(pageInfo == null) {
            return Msg.failed("api返回null");
        }
        return Msg.success().add("data", pageInfo);
    }

    @Override
    public Msg addRoles(String rolesName) {
        if(rolesName == null || rolesName.isEmpty()) {
            return Msg.failed("参数不能为空");
        }
        if(rolesName.equals("admin")) {
            return Msg.failed("角色名不能是admin");
        }
        boolean result = userApi.addRoles(rolesName);
        if(result) {
            return Msg.success("添加成功");
        } else {
            return Msg.failed("添加失败");
        }
    }
}
