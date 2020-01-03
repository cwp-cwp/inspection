package com.puzek.platform.inspection.service;

import com.puzek.platform.inspection.common.Msg;
import com.puzek.platform.inspection.entity.Parking;
import org.springframework.web.multipart.MultipartFile;

public interface ParkingService {
    Msg addParking(Parking parking);

    Msg getParkList(String parkingNumber, Integer area, int page, int pageSize);

    Msg modifyParking(Integer id, String parkingNumber, Integer area);

    Msg delParking(Integer id);

    Msg getParkList2(String parkingNumber, String area, int page, int pageSize);

    Msg getAllParkList();

    Msg getParkList3(String parkingNumber, Integer area, String street, String startTime, String endTime, int page, int pageSize);

    Msg exportFile(String parkingNumber, Integer area, String street, String startTime, String endTime);

    Msg importFile(MultipartFile file);
}
