package com.puzek.platform.inspection.serviceImpl;

import com.cwp.cloud.api.MessageDataApi;
import com.cwp.cloud.bean.MessageData;
import com.cwp.cloud.bean.PageResult;
import com.cwp.cloud.bean.Tag;
import com.puzek.platform.inspection.common.Msg;
import com.puzek.platform.inspection.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultServiceImpl implements ResultService {
    private final MessageDataApi messageDataApi;

    @Autowired
    public ResultServiceImpl(MessageDataApi messageDataApi) {
        this.messageDataApi = messageDataApi;
    }

    @Override
    public Msg getResultList(String patrolCarNumber, String areaName, String batchNumber, String parkNumber, String type, String pushStatus, String startTime, String endTime, int numPage, int rows, String tag) {
        PageResult<MessageData> result;
        if (tag == null || tag.length() == 0) {
            result = messageDataApi.getMessageData(patrolCarNumber, areaName, batchNumber, parkNumber, type, pushStatus, null, startTime, endTime, numPage, rows);
        } else {
            result = messageDataApi.getMessageData(patrolCarNumber, areaName, batchNumber, parkNumber, type, pushStatus, Tag.valueOf(tag), startTime, endTime, numPage, rows);
        }
        if (result == null) {
            return Msg.failed("Api返回null");
        }
        return Msg.success().add("data", result);
    }

    @Override
    public Msg getBatchNumber(String patrolCarNumber, String startTime, String endTime) {
        List<String> list = messageDataApi.getBatchNumByPatrolCarNum(patrolCarNumber, startTime, endTime);
        if (list == null) {
            return Msg.failed("Api返回null");
        }
        return Msg.success().add("data", list);
    }

    @Override
    public Msg modifyResult(String patrolCarNumber, String areaName, String batchNumber, String parkNumber, String newCarNumber) {
        boolean result = messageDataApi.updateCarNumber(patrolCarNumber, areaName, batchNumber, parkNumber, newCarNumber);
        if (result) {
            return Msg.success("修改成功");
        } else {
            return Msg.failed("修改失败");
        }
    }


    /*private final ResultMapper resultMapper;
    private final ResultImageMapper resultImageMapper;

    @Autowired
    public ResultServiceImpl(ResultMapper resultMapper, ResultImageMapper resultImageMapper) {
        this.resultMapper = resultMapper;
        this.resultImageMapper = resultImageMapper;
    }

    @Override
    public Msg getResultList(String batchNumber, String parkingNumber, String resultType, String carNumber, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<Result> resultList = resultMapper.getResultList(batchNumber, parkingNumber, resultType, carNumber);
        PageInfo<Result> pageInfo = new PageInfo<>(resultList);
        return Msg.success().add("data", pageInfo);
    }

    @Override
    public Msg addResult(String resultParam) {
        if (resultParam == null || resultParam.isEmpty()) {
            return Msg.failed("参数为空");
        }
        List<Result> list;
        try {
            list = JsonUtil.fromJson(resultParam, new TypeToken<ArrayList<Result>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
            return Msg.failed("参数转换错误，请检查");
        }
        if (list != null && list.size() > 0) {
            for (Result result : list) {
                resultMapper.insert(result);
                for (ResultImage resultImage : result.getResultImages()) {
                    resultImageMapper.insert(resultImage);
                }
            }
        }
        return Msg.failed("收到参数size为0");
    }*/


}
