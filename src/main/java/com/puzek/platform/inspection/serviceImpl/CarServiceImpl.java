package com.puzek.platform.inspection.serviceImpl;

import com.cwp.cloud.api.TopicApi;
import com.cwp.cloud.utils.IdWorker;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.puzek.platform.inspection.common.Msg;
import com.puzek.platform.inspection.dao.CarMapper;
import com.puzek.platform.inspection.dao.ParkingMapper;
import com.puzek.platform.inspection.entity.Car;
import com.puzek.platform.inspection.entity.Parking;
import com.puzek.platform.inspection.service.CarService;
import com.puzek.platform.inspection.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl implements CarService {
    private final CarMapper carMapper;
    private final ParkingMapper parkingMapper;
    private final TopicApi topicApi;
    private final IdWorker idWorker;

    @Autowired
    public CarServiceImpl(CarMapper carMapper, ParkingMapper parkingMapper, TopicApi topicApi, IdWorker idWorker) {
        this.carMapper = carMapper;
        this.parkingMapper = parkingMapper;
        this.topicApi = topicApi;
        this.idWorker = idWorker;
    }

    @Override
    public Msg addCar(String license, int area) {
        if (license == null || license.isEmpty()) {
            return Msg.failed("参数为空");
        }
        // 判断车牌是否已经存在
        Car car = carMapper.getCarByLicense(license);
        if (car != null) {
            return Msg.failed("车牌已存在");
        }
        String id = String.valueOf(idWorker.nextId()).substring(10);
        int result = carMapper.insert(Integer.parseInt(id), license, area);
        if (result > 0) {
            return Msg.success("添加成功");
        }
        return Msg.failed("添加失败");
    }

    @Override
    public Msg getCarList(String license, Integer area, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<Car> carList = this.carMapper.getCarList(license, area);
        PageInfo<Car> pageInfo = new PageInfo<>(carList);
        return Msg.success().add("data", pageInfo);
    }

    @Override
    public Msg login(String license, String position) {
        if (license == null || license.isEmpty()) {
            return Msg.failed("参数为空");
        }
        Car car = carMapper.getCarByLicense(license);
        if (car == null) {
            return Msg.failed("车牌不存在");
        }
        List<Parking> parkingList;
        String loginTime = DateUtil.getFormatDate();
        if (car.getLoginTime() == null || car.getLoginTime().isEmpty()) {
            parkingList = parkingMapper.getParkingList(null, car.getArea());
        } else {
            // 先获取当前时间和上次登录时间发生变化的车位
            //parkingList = parkingMapper.getChangeParking(car.getArea(), car.getLoginTime(), loginTime);
            // 获取全部吧
            parkingList = parkingMapper.getChangeParking(car.getArea(), "", loginTime);
        }
        carMapper.updateCarLoginTimeAndPosition(car.getId(), loginTime, position);
        topicApi.addThreeLevelTopic(license);
        return Msg.success("登录成功").add("data", car).add("parkingList", parkingList);
    }

    @Override
    public Msg modifyCar(Integer id, String license, Integer area) {
        if (id == null) {
            return Msg.failed("参数错误");
        }
        Car car = new Car();
        car.setId(id);
        car.setLicense(license);
        car.setArea(area);
        int result = carMapper.modifyCar(car);
        if (result > 0) {
            return Msg.success("修改成功");
        }
        return Msg.failed("修改失败");
    }

    @Override
    public Msg getCarListByArea(Integer id) {
        List<Car> carList = carMapper.getCarListByArea(id);
        return Msg.success().add("data", carList);
    }
}
