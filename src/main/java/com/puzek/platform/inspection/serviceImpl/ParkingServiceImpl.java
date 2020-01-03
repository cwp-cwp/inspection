package com.puzek.platform.inspection.serviceImpl;

import com.cwp.cloud.utils.IdWorker;
import com.cwp.cloud.utils.JsonUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.puzek.platform.inspection.common.AllConstant;
import com.puzek.platform.inspection.common.Msg;
import com.puzek.platform.inspection.dao.ParkingMapper;
import com.puzek.platform.inspection.entity.Parking;
import com.puzek.platform.inspection.service.ParkingService;
import com.puzek.platform.inspection.util.DateUtil;
import com.puzek.platform.inspection.util.ExcelUtil;
import com.puzek.platform.inspection.util.JsonUtil;
import com.puzek.platform.inspection.util.UploadFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import puzekcommon.utils.ObjUtil;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParkingServiceImpl implements ParkingService {
    private final ParkingMapper parkingMapper;
    private final IdWorker idWorker;
    private final static String[] EXCEL_HEADER = {"id", "车位号", "区域", "街道", "x1", "y1", "x2", "y2", "x3", "y3", "x4", "y4", "区域id"};
    private final static int[] EXCEL_columns = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};

    @Autowired
    public ParkingServiceImpl(ParkingMapper parkingMapper, IdWorker idWorker) {
        this.parkingMapper = parkingMapper;
        this.idWorker = idWorker;
    }


    @Override
    public Msg addParking(Parking parking) {
        if (parking.getParkingNumber() == null || parking.getParkingNumber().isEmpty()) {
            return Msg.failed("参数为空");
        }
        if (parking.getX1() == null || parking.getX1().isEmpty() || parking.getY1() == null || parking.getY1().isEmpty()) {
            return Msg.failed("参数为空");
        }
        if (parking.getX2() == null || parking.getX2().isEmpty() || parking.getY2() == null || parking.getY2().isEmpty()) {
            return Msg.failed("参数为空");
        }
        if (parking.getX3() == null || parking.getX3().isEmpty() || parking.getY3() == null || parking.getY3().isEmpty()) {
            return Msg.failed("参数为空");
        }
        if (parking.getX4() == null || parking.getX4().isEmpty() || parking.getY4() == null || parking.getY4().isEmpty()) {
            return Msg.failed("参数为空");
        }
        // 判断车位号是否存在
        Parking p = parkingMapper.getParkingByParkingNumberAndArea(parking.getParkingNumber(), parking.getArea());
        if (p != null) {
            return Msg.failed("该区域中车位号已存在");
        }
        parking.setOperateTime(DateUtil.getFormatDate());
        parking.setStatus("add");
        String id = String.valueOf(idWorker.nextId()).substring(10);
        parking.setId(Integer.parseInt(id));
        int result = parkingMapper.insert(parking);
        if (result > 0) {
            return Msg.success("添加成功");
        }
        return Msg.failed("添加失败");
    }

    @Override
    public Msg getParkList(String parkingNumber, Integer area, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<Parking> parkingList = parkingMapper.getParkingList(parkingNumber, area);
        PageInfo<Parking> pageInfo = new PageInfo<>(parkingList);
        return Msg.success().add("data", pageInfo);
    }

    @Override
    public Msg modifyParking(Integer id, String parkingNumber, Integer area) {
        if (id == null || area == null) {
            return Msg.failed("参数为空");
        }
        // 判断车位号是否存在
        Parking p = parkingMapper.getParkingByParkingNumberAndArea(parkingNumber, area);
        if (p != null) {
            return Msg.failed("该区域中车位号已存在");
        }
        int result = parkingMapper.modifyParking(id, parkingNumber, area);
        if (result > 0) {
            return Msg.success("修改成功");
        }
        return Msg.failed("修改失败");
    }

    @Override
    public Msg delParking(Integer id) {
        if (id == null) {
            return Msg.failed("参数为空");
        }
        // 判断车位号是否存在
        Parking p = parkingMapper.getParkingById(id);
        if (p == null) {
            return Msg.failed("车位不存在");
        }
        Parking parking = new Parking();
        parking.setId(id);
        parking.setOperateTime(DateUtil.getFormatDate());
        parking.setStatus("del");
        int result = parkingMapper.modifyParkingByParams(parking);
        if (result > 0) {
            return Msg.success("删除成功");
        }
        return Msg.failed("删除失败");
    }

    @Override
    public Msg getParkList2(String parkingNumber, String area, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<Parking> parkingList;
        if (area == null || area.isEmpty()) {
            parkingList = parkingMapper.getParkingList(parkingNumber, null);
        } else {
            parkingList = parkingMapper.getParkingList(parkingNumber, Integer.valueOf(area));
        }

        PageInfo<Parking> pageInfo = new PageInfo<>(parkingList);
        return Msg.success().add("data", pageInfo.getList()).add("totalPage", String.valueOf(pageInfo.getPages()));
    }

    @Override
    public Msg getAllParkList() {
        List<Parking> parkingList = parkingMapper.getAllParkingList();
        return Msg.success().add("data", parkingList);
    }

    @Override
    public Msg getParkList3(String parkingNumber, Integer area, String street, String startTime, String endTime, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<Parking> parkingList = parkingMapper.getParkingList3(parkingNumber, area, street, startTime, endTime);
        PageInfo<Parking> pageInfo = new PageInfo<>(parkingList);
        return Msg.success().add("data", pageInfo);
    }

    @Override
    public Msg exportFile(String parkingNumber, Integer area, String street, String startTime, String endTime) {
        List<Parking> parkingList = parkingMapper.getParkingList3(parkingNumber, area, street, startTime, endTime);
        List<List<String>> list = new ArrayList<>();
        for (Parking parking : parkingList) {
            List<String> rowList = new ArrayList<>();
            rowList.add(String.valueOf(parking.getId()));
            rowList.add(parking.getParkingNumber());
            rowList.add(parking.getAreaName());
            rowList.add(parking.getStreetName());
            rowList.add(parking.getX1());
            rowList.add(parking.getY1());
            rowList.add(parking.getX2());
            rowList.add(parking.getY2());
            rowList.add(parking.getX3());
            rowList.add(parking.getY3());
            rowList.add(parking.getX4());
            rowList.add(parking.getY4());
            rowList.add(String.valueOf(parking.getArea()));
            list.add(rowList);
        }
        String realPath = ExcelUtil.writeExcel("UploadFile/exportParking", "parking", EXCEL_HEADER, null, list);
        return realPath == null
                ? Msg.failed("导出失败")
                : Msg.success().add("data", ExcelUtil.getRelativePath(realPath));
    }

    @Override
    public Msg importFile(MultipartFile file) {
        if (file == null) {
            return Msg.failed("请添加excel文件");
        }
        Msg msg = UploadFileUtil.uploadMultipartFile(file, "UploadFile/importParking");
        if (msg.getResult().equals(AllConstant.RESULT_FAILED)) {
            return msg;
        }
        List<List<String>> sheetData = ExcelUtil.readRowsExcel(msg.getMsg(), EXCEL_columns);
        if (sheetData == null || sheetData.size() == 0) {
            return Msg.failed("excel数据为空");
        }
        System.out.println(JsonUtil.toJson(sheetData.get(0)));
        String titleError = ExcelUtil.excelTitleJudge(sheetData.get(0), EXCEL_HEADER);
        if (titleError != null) {
            return Msg.failed(titleError);
        }

        for (int i = 1; i < sheetData.size(); i++) {
            for (int y = 0; y < sheetData.get(i).size(); y++) {
                if (ObjUtil.isEmpty(sheetData.get(i).get(y))) {
                    return Msg.failed("Excel第 " + (i + 1) + " 行第 " + (y + 1) + " 列数据错误，不允许为空");
                }
            }
        }

        Integer successModifyCount = 0;
        Integer successAddCount = 0;
        for (int i = 1; i < sheetData.size(); i++) {
            //Parking parking = parkingMapper.getParkingById(Integer.valueOf(sheetData.get(i).get(0)));
            Parking parking = parkingMapper.getParkingByParkingNumberAndArea(sheetData.get(i).get(1), Integer.valueOf(sheetData.get(i).get(12)));
            if (parking != null) {
                Parking p = new Parking();
                // {"id", "车位号", "区域", "街道", "x1", "y1", "x2", "y2", "x3", "y3", "x4", "y4", "区域id"};
                p.setId(Integer.valueOf(sheetData.get(i).get(0)));
                p.setParkingNumber(sheetData.get(i).get(1));
                p.setStreetName(sheetData.get(i).get(3));
                p.setX1(sheetData.get(i).get(4));
                p.setY1(sheetData.get(i).get(5));
                p.setX2(sheetData.get(i).get(6));
                p.setY2(sheetData.get(i).get(7));
                p.setX3(sheetData.get(i).get(8));
                p.setY3(sheetData.get(i).get(9));
                p.setX4(sheetData.get(i).get(10));
                p.setY4(sheetData.get(i).get(11));
                int result = parkingMapper.modifyParkingByParams(p);
                if (result > 0) {
                    successModifyCount += 1;
                }
            } else {
                Parking p = new Parking();
                // {"id", "车位号", "区域", "街道", "x1", "y1", "x2", "y2", "x3", "y3", "x4", "y4", "区域id"};
                p.setId(Integer.valueOf(sheetData.get(i).get(0)));
                p.setParkingNumber(sheetData.get(i).get(1));
                p.setStreetName(sheetData.get(i).get(3));
                p.setX1(sheetData.get(i).get(4));
                p.setY1(sheetData.get(i).get(5));
                p.setX2(sheetData.get(i).get(6));
                p.setY2(sheetData.get(i).get(7));
                p.setX3(sheetData.get(i).get(8));
                p.setY3(sheetData.get(i).get(9));
                p.setX4(sheetData.get(i).get(10));
                p.setY4(sheetData.get(i).get(11));
                p.setArea(Integer.valueOf(sheetData.get(i).get(12)));
                p.setStatus("add");
                int result = parkingMapper.insert2(p);
                if (result > 0) {
                    successAddCount += 1;
                }

            }
        }
        return Msg.success("成功更新：" + successModifyCount + " 条, 添加：" + successAddCount + " 条数据");
    }
}
