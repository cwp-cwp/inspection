package com.puzek.platform.inspection.controller;

import com.puzek.platform.inspection.common.Msg;
import com.puzek.platform.inspection.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/car")
public class CarController {
    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @RequestMapping("/addCar")
    @ResponseBody
    public Msg addCar(String license, int area) {
        return carService.addCar(license, area);
    }

    @RequestMapping("/getCarList")
    @ResponseBody
    public Msg getCarList(String license, Integer area, int page, int pageSize) {
        return carService.getCarList(license, area, page, pageSize);
    }

    @RequestMapping("/login")
    @ResponseBody
    public Msg login(String license, String position) {
        return carService.login(license, position);
    }

    @RequestMapping("/modifyCar")
    @ResponseBody
    public Msg modifyCar(Integer id, String license, Integer area) {
        return carService.modifyCar(id, license, area);
    }

    @RequestMapping("/getCarListByArea")
    @ResponseBody
    public Msg getCarListByArea(Integer id) {
        return carService.getCarListByArea(id);
    }
}
