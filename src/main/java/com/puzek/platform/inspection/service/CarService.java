package com.puzek.platform.inspection.service;

import com.puzek.platform.inspection.common.Msg;

public interface CarService {
    Msg addCar(String license, int area);

    Msg getCarList(String license, Integer area, int page, int pageSize);

    Msg login(String license, String position);

    Msg modifyCar(Integer id, String license, Integer area);

    Msg getCarListByArea(Integer id);
}
