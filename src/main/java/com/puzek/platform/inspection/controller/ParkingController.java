package com.puzek.platform.inspection.controller;

import com.puzek.platform.inspection.common.Msg;
import com.puzek.platform.inspection.entity.Parking;
import com.puzek.platform.inspection.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/parking")
public class ParkingController {
    private final ParkingService parkingService;

    @Autowired
    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @RequestMapping("/addParking")
    @ResponseBody
    public Msg addParking(Parking parking) {
        return parkingService.addParking(parking);
    }

    @RequestMapping("/getParkList")
    @ResponseBody
    public Msg getParkList(String parkingNumber, Integer area, int page, int pageSize) {
        return parkingService.getParkList(parkingNumber, area, page, pageSize);
    }

    @RequestMapping("/getParkList2")
    @ResponseBody
    public Msg getParkList2(String parkingNumber, String area, int page, int pageSize) {
        return parkingService.getParkList2(parkingNumber, area, page, pageSize);
    }

    @RequestMapping("/getParkList3")
    @ResponseBody
    public Msg getParkList3(String parkingNumber, Integer area, String street, String startTime, String endTime, int page, int pageSize) {
        return parkingService.getParkList3(parkingNumber, area, street, startTime, endTime, page, pageSize);
    }

    @RequestMapping("/exportFile")
    @ResponseBody
    public Msg exportFile(String parkingNumber, Integer area, String street, String startTime, String endTime) {
        return parkingService.exportFile(parkingNumber, area, street, startTime, endTime);
    }

    @RequestMapping("/ImportFile")
    @ResponseBody
    public Msg importFile(MultipartFile file) {
        return parkingService.importFile(file);
    }

    @RequestMapping("/getAllParkList")
    @ResponseBody
    public Msg getAllParkList() {
        return parkingService.getAllParkList();
    }

    @RequestMapping("/modifyParking")
    @ResponseBody
    public Msg modifyParking(Integer id, String parkingNumber, Integer area) {
        return parkingService.modifyParking(id, parkingNumber, area);
    }

    @RequestMapping("/delParking")
    @ResponseBody
    public Msg delParking(Integer id) {
        return parkingService.delParking(id);
    }
}
