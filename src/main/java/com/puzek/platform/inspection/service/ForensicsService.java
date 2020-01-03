package com.puzek.platform.inspection.service;

import com.puzek.platform.inspection.common.Msg;
import org.springframework.web.multipart.MultipartFile;

public interface ForensicsService {
    Msg uploadImage(MultipartFile[] images, String carNumber);
}
