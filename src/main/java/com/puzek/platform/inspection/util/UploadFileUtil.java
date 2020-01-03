package com.puzek.platform.inspection.util;

import com.puzek.platform.inspection.common.Msg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class UploadFileUtil {
    private final static Logger LOG = LoggerFactory.getLogger(UploadFileUtil.class);

    public static Msg uploadMultipartFile(MultipartFile multipartFile, String filePath) {
        // 开始转存
        try {
            String realPath = ResourceUtils.getFile("classpath:../../").getPath();
            // 设置转存的具体路径，即 filePath + "/" + 文件名
            String finalFilePath = realPath + File.separator + filePath + File.separator + multipartFile.getOriginalFilename();
            // 判断路径是否存在，不存在就新建
            File file = new File(realPath + File.separator + filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            LOG.info("UploadFileUtil.finalFilePath：" + finalFilePath);
            multipartFile.transferTo(new File(finalFilePath));
            return Msg.success(finalFilePath);
        } catch (IOException e) {
            LOG.warn("error occur in UploadFileUtil.uploadMultipartFile：", e);
            return Msg.failed("文件转存异常"); //TODO 双语
        }
    }

    public static Msg uploadMultipartFileInAbsolutePath(MultipartFile multipartFile, String filePath) {
        // 开始转存
        try {
            // 设置转存的具体路径，即 filePath + "/" + 文件名
            String finalFilePath = filePath + File.separator + multipartFile.getOriginalFilename();
            // 判断路径是否存在，不存在就新建
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            LOG.info("UploadFileUtil.uploadMultipartFileInAbsolutePath：" + finalFilePath);
            multipartFile.transferTo(new File(finalFilePath));
            return Msg.success(finalFilePath);
        } catch (IOException e) {
            LOG.warn("error occur in UploadFileUtil.uploadMultipartFileInAbsolutePath：", e);
            return Msg.failed("文件转存异常"); //TODO 双语
        }
    }
}
