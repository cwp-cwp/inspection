package com.puzek.platform.inspection.dao;

import com.puzek.platform.inspection.entity.Car;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CarMapper {
    int insert(@Param("id") int id, @Param("license") String license, @Param("area") Integer area);
    List<Car> getCarList(@Param("license") String license, @Param("area") Integer area);
    List<Car> getCarListByArea(@Param("area") Integer area);
    Car getCarByLicense(@Param("license") String license);
    int modifyCar(Car car);
    int updateCarLoginTimeAndPosition(@Param("id") Integer id, @Param("loginTime") String loginTime, @Param("position") String position);
}
