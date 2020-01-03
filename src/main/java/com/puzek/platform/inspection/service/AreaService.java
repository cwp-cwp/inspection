package com.puzek.platform.inspection.service;

import com.puzek.platform.inspection.common.Msg;

public interface AreaService {
    Msg addArea(String areaName, String operation, String pushUrl);

    Msg getAreaList(String areaName, String operation, int page, int pageSize);

    Msg getAllAreaList();

    Msg modifyArea(Integer id, String areaName, String operation, String pushUrl);
}
