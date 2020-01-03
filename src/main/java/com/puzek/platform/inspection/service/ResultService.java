package com.puzek.platform.inspection.service;

import com.puzek.platform.inspection.common.Msg;

public interface ResultService {
    Msg getResultList(String patrolCarNumber, String areaName, String batchNumber, String parkNumber, String type, String pushStatus, String startTime, String endTime, int numPage, int rows, String tag);

    Msg getBatchNumber(String patrolCarNumber, String startTime, String endTime);

    Msg modifyResult(String patrolCarNumber, String areaName, String batchNumber, String parkNumber, String newCarNumber);

    /*Msg getResultList(String batchNumber, String parkingNumber, String resultType, String carNumber, int page, int pageSize);

    Msg addResult(String result);*/
}
