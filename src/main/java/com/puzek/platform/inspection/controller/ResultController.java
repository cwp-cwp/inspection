package com.puzek.platform.inspection.controller;

import com.puzek.platform.inspection.common.Msg;
import com.puzek.platform.inspection.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/result")
public class ResultController {
    private final ResultService resultService;

    @Autowired
    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }

    /*@RequestMapping("/getResultList")
    @ResponseBody
    public Msg getResultList(String batchNumber, String parkingNumber, String resultType, String carNumber, int page, int pageSize) {
        return resultService.getResultList(batchNumber, parkingNumber, resultType, carNumber,page, pageSize);
    }

    @RequestMapping("/addResult")
    @ResponseBody
    public Msg addResult(String result) {
        return resultService.addResult(result);
    }*/

    @RequestMapping("/getResultList")
    @ResponseBody
    public Msg getResultList(String patrolCarNumber, String areaName, String batchNumber, String parkNumber, String type, String pushStatus, String startTime, String endTime, int numPage, int rows, String tag) {
        return resultService.getResultList(patrolCarNumber, areaName, batchNumber, parkNumber, type, pushStatus, startTime, endTime, numPage, rows, tag);
    }

    @RequestMapping("/getBatchNumber")
    @ResponseBody
    public Msg getBatchNumber(String patrolCarNumber, String startTime, String endTime) {
        return resultService.getBatchNumber(patrolCarNumber, startTime, endTime);
    }

    @RequestMapping("/modifyResult")
    @ResponseBody
    public Msg modifyResult(String patrolCarNumber, String areaName, String batchNumber, String parkNumber, String newCarNumber) {
        return resultService.modifyResult(patrolCarNumber, areaName, batchNumber, parkNumber, newCarNumber);
    }

}
