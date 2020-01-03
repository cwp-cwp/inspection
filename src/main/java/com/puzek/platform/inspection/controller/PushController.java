package com.puzek.platform.inspection.controller;

import com.puzek.platform.inspection.common.Msg;
import com.puzek.platform.inspection.service.PushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/push")
public class PushController {
    private final PushService pushService;

    @Autowired
    public PushController(PushService pushService) {
        this.pushService = pushService;
    }

    @RequestMapping("/doPush")
    @ResponseBody
    public Msg doPush(String batchNumber) {
        return pushService.doPush(batchNumber);
    }
}
