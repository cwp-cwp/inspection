package com.puzek.platform.inspection.serviceImpl;

import com.puzek.platform.inspection.common.Msg;
import com.puzek.platform.inspection.service.ForensicsService;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public class ForensicsImpl implements ForensicsService {
    @Override
    public Msg uploadImage(MultipartFile[] images, String carNumber) {
        if (images != null) {
            if(carNumber == null || carNumber.length() == 0) {
                return Msg.failed("车牌号不能未空");
            }
            String realPath = null;
            try {
                realPath = ResourceUtils.getFile("classpath:../../").getPath();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            File f = new File(realPath + File.separator + "forensicsImage" + File.separator + carNumber);
            if (!f.exists()) {
                f.mkdirs();
            }
            for (MultipartFile image : images) {
                String finalFilePath = realPath + File.separator + "forensicsImage" + File.separator + carNumber + File.separator + image.getOriginalFilename();
                try {
                    image.transferTo(new File(finalFilePath));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return Msg.success("成功上传" + images.length + "张照片");
        }
        return Msg.failed("照片文件为空");
    }
}
