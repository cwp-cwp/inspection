package com.puzek.platform.inspection.dao;

import com.puzek.platform.inspection.entity.Area;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AreaMapper {
    int insert(@Param("id") int id, @Param("name") String name, @Param("operation") String operation, @Param("pushUrl") String pushUrl);
    List<Area> getAreaList(@Param("name") String name, @Param("operation") String operation);
    List<Area> getAllAreaList();
    Area getAreaByName(@Param("name") String name);
    int modifyArea(Area area);
}
