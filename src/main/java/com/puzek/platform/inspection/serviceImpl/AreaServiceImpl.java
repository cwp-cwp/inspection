package com.puzek.platform.inspection.serviceImpl;

import com.cwp.cloud.utils.IdWorker;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.puzek.platform.inspection.common.Msg;
import com.puzek.platform.inspection.dao.AreaMapper;
import com.puzek.platform.inspection.entity.Area;
import com.puzek.platform.inspection.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaServiceImpl implements AreaService {
    private final AreaMapper areaMapper;

    @Autowired
    public AreaServiceImpl(AreaMapper areaMapper) {
        this.areaMapper = areaMapper;
    }

    @Autowired
    private IdWorker idWorker;

    @Override
    public Msg addArea(String areaName, String operation, String pushUrl) {
        if (areaName == null || areaName.isEmpty() || operation == null || operation.isEmpty() || pushUrl == null || pushUrl.isEmpty()) {
            return Msg.failed("参数为空");
        }
        // 判断区域名称是否存在
        Area area = areaMapper.getAreaByName(areaName);
        if (area != null) {
            return Msg.failed("该区域已存在");
        }
        String id = String.valueOf(idWorker.nextId()).substring(10);
        int result = areaMapper.insert(Integer.parseInt(id), areaName, operation, pushUrl);
        if (result > 0) {
            return Msg.success("添加成功");
        }
        return Msg.failed("添加失败");
    }

    @Override
    public Msg getAreaList(String areaName, String operation, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<Area> areaList = areaMapper.getAreaList(areaName, operation);
        PageInfo<Area> pageInfo = new PageInfo<>(areaList);
        return Msg.success().add("data", pageInfo);
    }

    @Override
    public Msg getAllAreaList() {
        List<Area> areaList = areaMapper.getAllAreaList();
        return Msg.success().add("data", areaList);
    }

    @Override
    public Msg modifyArea(Integer id, String areaName, String operation, String pushUrl) {
        if (id == null) {
            return Msg.failed("参数为空");
        }
        Area area = new Area();
        area.setId(id);
        area.setAreaName(areaName);
        area.setOperation(operation);
        area.setPushUrl(pushUrl);
        int result = areaMapper.modifyArea(area);
        if (result > 0) {
            return Msg.success("修改成功");
        }
        return Msg.failed("修改失败");
    }
}
