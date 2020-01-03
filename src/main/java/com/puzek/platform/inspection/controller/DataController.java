package com.puzek.platform.inspection.controller;

import com.puzek.platform.inspection.common.Msg;
import com.puzek.platform.inspection.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/data")
public class DataController {
    private final DataService dataService;

    @Autowired
    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

    @RequestMapping("/getCheckStatistics")
    @ResponseBody
    public Msg getCheckStatistics() {
        return dataService.getCheckStatistics();
    }
}
