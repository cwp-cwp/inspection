package com.puzek.platform.inspection.service;

import com.puzek.platform.inspection.common.Msg;

public interface CheckService {
    Msg getOneCheckInfo(String timeValue);

    Msg checkInfo(String batchNumber, String parkNumber, String newCarNumber);

    Msg getCheckInfoList(Integer userId, String startTime, String endTime, int page, int pageSize);

    Msg getErrorCheckInfoList(Integer userId, String startTime, String endTime, int page, int pageSize);

    Msg getOneDayChetCount();
}
