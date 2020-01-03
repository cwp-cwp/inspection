package com.puzek.platform.inspection.controller;

import com.puzek.platform.inspection.common.Msg;
import com.puzek.platform.inspection.service.RolesService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/roles")
public class RolesController {
    private final RolesService rolesService;

    public RolesController(RolesService rolesService) {
        this.rolesService = rolesService;
    }

    @RequestMapping("/getAllRoles")
    @ResponseBody
    public Msg getAllRoles() {
        return rolesService.getAllRoles();
    }

    @RequestMapping("/getRolesList")
    @ResponseBody
    public Msg getRolesList(String rolesName, int page, int pageSize) {
        return rolesService.getRolesList(rolesName, page, pageSize);
    }

    @RequestMapping("/addRoles")
    @ResponseBody
    public Msg addRoles(String rolesName) {
        return rolesService.addRoles(rolesName);
    }
}
