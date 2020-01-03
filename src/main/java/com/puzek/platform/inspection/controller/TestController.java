package com.puzek.platform.inspection.controller;

import com.puzek.platform.inspection.common.Msg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@Controller
@RequestMapping("/puzekTest")
public class TestController {
    private final static Logger logger = LoggerFactory.getLogger(TestController.class);
    private final static Logger LOG = LoggerFactory.getLogger(TestController.class);

    @RequestMapping("/testlog")
    @ResponseBody
    public Msg getUserList() {
        logger.info("aaaa");
        logger.warn("bbbb");
        logger.error("cccc");

        LOG.info("aaaa1");
        LOG.warn("bbbb1");
        LOG.error("cccc1");
        return Msg.success();
    }

    @RequestMapping("/uploadImage")
    @ResponseBody
    public Msg uploadImage(MultipartFile[] images, String batchNumber, String parkNumber) {
        System.out.println("uploadImage:" + batchNumber + "_" + parkNumber);
        if (images != null) {
            System.out.println(images.length);
            String realPath = null;
            try {
                realPath = ResourceUtils.getFile("classpath:../../").getPath();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            // 判断路径是否存在，不存在就新建
            File f = new File(realPath + File.separator + "scanImage" + File.separator + batchNumber);
            if (!f.exists()) {
                f.mkdirs();
            }
            for (MultipartFile image : images) {
                String finalFilePath = realPath + File.separator + "scanImage" + File.separator + batchNumber + File.separator + image.getOriginalFilename();
                try {
                    image.transferTo(new File(finalFilePath));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return Msg.success();
    }

    @RequestMapping("/uploadImage2")
    @ResponseBody
    public Msg uploadImage2(MultipartFile file, String batchNumber, String parkNumber) {
        System.out.println("uploadImage2:" + batchNumber + "_" + parkNumber);
        String realPath = null;
        try {
            realPath = ResourceUtils.getFile("classpath:../../").getPath();
            // 设置转存的具体路径，即 filePath + "/" + 文件名
            String finalFilePath = realPath + File.separator + "scanImage" + File.separator + batchNumber + File.separator + file.getOriginalFilename();
            // 判断路径是否存在，不存在就新建
            File f = new File(realPath + File.separator + "scanImage" + File.separator + batchNumber);
            if (!f.exists()) {
                f.mkdirs();
            }
            LOG.info("UploadFileUtil.finalFilePath：" + finalFilePath);
            file.transferTo(new File(finalFilePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Msg.success();
    }
}
