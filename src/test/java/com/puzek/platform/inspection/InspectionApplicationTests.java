package com.puzek.platform.inspection;

import com.github.pagehelper.PageInfo;
import com.google.gson.reflect.TypeToken;
import com.puzek.platform.inspection.common.Msg;
import com.puzek.platform.inspection.dao.AreaMapper;
import com.puzek.platform.inspection.dao.ResultMapper;
import com.puzek.platform.inspection.entity.Area;
import com.puzek.platform.inspection.entity.Car;
import com.puzek.platform.inspection.entity.Parking;
import com.puzek.platform.inspection.entity.Result;
import com.puzek.platform.inspection.service.AreaService;
import com.puzek.platform.inspection.service.CarService;
import com.puzek.platform.inspection.util.JsonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InspectionApplicationTests {
    @Autowired
    private AreaMapper areaMapper;
    @Autowired
    private ResultMapper resultMapper;
    @Autowired
    private CarService carService;
    @Autowired
    private AreaService areaService;
	@Test
	public void contextLoads() {
	}
	@Test
    public void getCarListTest() {
        Msg msg = carService.getCarList("", 1, 1, 10);
        PageInfo<Car> pageInfo = (PageInfo<Car>) msg.getExtend().get("data");
        System.out.println(pageInfo.getList().size());
        for (Car car : pageInfo.getList()) {
            System.out.println(car.getLicense() + " " + car.getAreaName() + " " + car.getOperation() + " " + car.getPushUrl());
        }
    }
    @Test
    public void addCarTest() {
	    Msg msg = carService.addCar("AAAAA", 1);
        System.out.println(msg.getMsg());
    }
    @Test
    public void addAreaTest() {
        Msg msg = areaService.addArea("龙华区", "parkmas", "pushUrl");
        System.out.println(msg.getMsg());
    }
    @Test
    public void getAreaTest() {
        Area area = areaMapper.getAreaByName("龙华区1");
        System.out.println(area);
    }

    @Test
    public void modifyAreaTest() {
        Area area = new Area();
        area.setId(3);
        area.setAreaName("宝安区");
        area.setOperation("ABC");
        area.setPushUrl("---");
        int result = areaMapper.modifyArea(area);
        System.out.println(result);
    }

    @Test
    public void carLoginTest() {
        carService.login("AAAAA", null);
    }

    @Test
    public void JsonTest() {
        Parking parking = new Parking();
        parking.setId(1);
        parking.setArea(1);
        parking.setParkingNumber("10001");
        parking.setX1("x1");
        parking.setY1("y1");
        parking.setX2("x2");
        parking.setY2("y2");
        parking.setX2("x3");
        parking.setY3("Y3");
        parking.setX4("x4");
        parking.setY4("y4");
        parking.setStatus("add");
        parking.setOperateTime("2018-09-10 12:12:12");
        parking.setAreaName("南山");
        String str = JsonUtil.toJson(Msg.success().add("data", parking));
        System.out.println(str);

        Msg msg = JsonUtil.fromJson(str, new TypeToken<Msg>() {
        }.getType());

        System.out.println(JsonUtil.toJson(msg.getExtend().get("data")));
    }
}
