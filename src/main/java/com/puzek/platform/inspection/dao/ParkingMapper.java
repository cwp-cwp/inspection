package com.puzek.platform.inspection.dao;

import com.puzek.platform.inspection.entity.Parking;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ParkingMapper {
    int insert(Parking parking);

    int insert2(Parking parking);

    List<Parking> getParkingList(@Param("parkingNumber") String parkingNumber, @Param("area") Integer area);

    List<Parking> getChangeParking(@Param("area") Integer area, @Param("lastTime") String lastTime, @Param("currentTime") String currentTime);

    List<Parking> getAllParkingList();

    List<Parking> getParkingList3(@Param("parkingNumber") String parkingNumber, @Param("area") Integer area, @Param("street") String street, @Param("startTime") String startTime, @Param("endTime") String endTime);

    Parking getParkingByParkingNumberAndArea(@Param("parkingNumber") String parkingNumber, @Param("area") Integer area);

    Parking getParkingById(@Param("id") Integer id);

    int modifyParking(@Param("id") Integer id, @Param("parkingNumber") String parkingNumber, @Param("area") Integer area);

    int modifyParkingByParams(Parking parking);
}
