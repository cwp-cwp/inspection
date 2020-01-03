package com.puzek.platform.inspection.controller;

import com.puzek.platform.inspection.common.Msg;
import com.puzek.platform.inspection.service.CheckService;
import com.sun.imageio.plugins.common.I18N;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/check")
public class CheckController {
    private final CheckService checkService;

    @Autowired
    public CheckController(CheckService checkService) {
        this.checkService = checkService;
    }

    @RequestMapping("/getOneCheckInfo")
    @ResponseBody
    public Msg getOneCheckInfo(String timeValue) {
        return checkService.getOneCheckInfo(timeValue);
    }

    @RequestMapping("/checkInfo")
    @ResponseBody
    public Msg checkInfo(String batchNumber, String parkNumber, String newCarNumber) {
        return checkService.checkInfo(batchNumber, parkNumber, newCarNumber);
    }

    @RequestMapping("/getCheckInfoList")
    @ResponseBody
    public Msg getCheckInfoList(Integer userId, String startTime, String endTime, int page, int pageSize) {
        return checkService.getCheckInfoList(userId, startTime, endTime, page, pageSize);
    }

    @RequestMapping("/getErrorCheckInfoList")
    @ResponseBody
    public Msg getErrorCheckInfoList(Integer userId, String startTime, String endTime, int page, int pageSize) {
        return checkService.getErrorCheckInfoList(userId, startTime, endTime, page, pageSize);
    }

    @RequestMapping("/getOneDayChetCount")
    @ResponseBody
    public Msg getOneDayChetCount() {
        return checkService.getOneDayChetCount();
    }
}
