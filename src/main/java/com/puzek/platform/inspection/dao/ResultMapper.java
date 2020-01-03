package com.puzek.platform.inspection.dao;

import com.puzek.platform.inspection.entity.Result;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ResultMapper {
    List<Result> getResultList(@Param("batchNumber") String batchNumber, @Param("parkingNumber") String parkingNumber, @Param("resultType") String resultType, @Param("carNumber") String carNumber);
    int insert(Result result);
}
