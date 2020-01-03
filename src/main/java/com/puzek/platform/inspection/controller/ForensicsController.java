package com.puzek.platform.inspection.controller;

import com.puzek.platform.inspection.common.Msg;
import com.puzek.platform.inspection.service.ForensicsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/forensics")
public class ForensicsController {
    private final ForensicsService forensicsService;

    @Autowired
    public ForensicsController(ForensicsService forensicsService) {
        this.forensicsService = forensicsService;
    }

    @RequestMapping("/uploadImage")
    @ResponseBody
    public Msg uploadImage(MultipartFile[] images, String carNumber) {
        return forensicsService.uploadImage(images, carNumber);
    }
}
