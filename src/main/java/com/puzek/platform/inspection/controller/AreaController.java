package com.puzek.platform.inspection.controller;

import com.puzek.platform.inspection.common.Msg;
import com.puzek.platform.inspection.service.AreaService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/area")
public class AreaController {
    private final AreaService areaService;

    public AreaController(AreaService areaService) {
        this.areaService = areaService;
    }

    @RequestMapping("/addArea")
    @ResponseBody
    public Msg addArea(String areaName, String operation, String pushUrl) {
        return areaService.addArea(areaName, operation, pushUrl);
    }

    @RequestMapping("/getAreaList")
    @ResponseBody
    public Msg getAreaList(String areaName, String operation, int page, int pageSize) {
        return areaService.getAreaList(areaName, operation, page, pageSize);
    }

    @RequestMapping("/getAllAreaList")
    @ResponseBody
    public Msg getAllAreaList() {
        return areaService.getAllAreaList();
    }

    @RequestMapping("/modifyArea")
    @ResponseBody
    public Msg modifyArea(Integer id, String areaName, String operation, String pushUrl) {
        return areaService.modifyArea(id, areaName, operation, pushUrl);
    }
}
