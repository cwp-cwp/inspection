package com.puzek.platform.inspection.serviceImpl;

import com.cwp.cloud.api.CheckApi;
import com.cwp.cloud.bean.user.Echarts;
import com.puzek.platform.inspection.common.Msg;
import com.puzek.platform.inspection.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataServiceImpl implements DataService {
    private final CheckApi checkApi;

    @Autowired
    public DataServiceImpl(CheckApi checkApi) {
        this.checkApi = checkApi;
    }

    @Override
    public Msg getCheckStatistics() {
        /*List<Echarts> echartsList = checkApi.getWorkingByTime2();
        if(echartsList == null) {
            return Msg.failed("Api返回null");
        }
        return Msg.success().add("data", echartsList);*/
        List<Echarts> echartsList = checkApi.getWorkingByTime();
        if (echartsList == null) {
            return Msg.failed("Api返回null");
        }
        return Msg.success().add("data", echartsList);
    }
}
