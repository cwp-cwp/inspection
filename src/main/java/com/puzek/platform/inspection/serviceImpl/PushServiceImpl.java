package com.puzek.platform.inspection.serviceImpl;

import com.cwp.cloud.api.MessageDataApi;
import com.cwp.cloud.bean.MessageData;
import com.cwp.cloud.bean.PushStatus;
import com.cwp.cloud.bean.ResultType;
import com.puzek.platform.inspection.common.Msg;
import com.puzek.platform.inspection.dao.CarMapper;
import com.puzek.platform.inspection.entity.Car;
import com.puzek.platform.inspection.entity.PushContent;
import com.puzek.platform.inspection.entity.PushResult;
import com.puzek.platform.inspection.service.PushService;
import com.puzek.platform.inspection.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import puzekcommon.utils.JsonUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class PushServiceImpl implements PushService {
    private final MessageDataApi messageDataApi;
    private final CarMapper carMapper;

    @Autowired
    public PushServiceImpl(MessageDataApi messageDataApi, CarMapper carMapper) {
        this.messageDataApi = messageDataApi;
        this.carMapper = carMapper;
    }

    @Override
    public Msg doPush(String batchNumber) {
        if (batchNumber == null || batchNumber.isEmpty()) {
            return Msg.failed("参数为空");
        }
        List<MessageData> list = messageDataApi.getAllMessageDataByBatchNumber(batchNumber);
        List<PushContent> pushContents = filter(list);
        for (PushContent pushContent : pushContents) {
            Car car = carMapper.getCarByLicense(pushContent.getPatrolCarNumber());
            if (car != null) {
                pushContent.setPushUrl(car.getPushUrl());
            } else {
                pushContent.setPushUrl("http://192.168.100.53:8080/scanCloud/");
            }
        }
        try {
            startPost(batchNumber, pushContents);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Msg.success("操作成功");
    }

    private List<PushContent> filter(List<MessageData> spaceDetails) {
        List<PushContent> result = new ArrayList<>();
        if (spaceDetails == null)
            return result;
        for (MessageData messageData : spaceDetails) {
            if (messageData.getType() == ResultType.ABNORMAL || messageData.getPushStatus() == PushStatus.SUCCESS)
                continue;
            result.add(new PushContent(messageData));
        }
        return result;
    }

    private List<PushResult> startPost(String batchNumber, List<PushContent> pushContent) throws Exception {
        List<PushResult> result = new ArrayList<>();
        for (PushContent p : pushContent) {
            List<PushContent> list = new ArrayList<>();
            list.add(p);
            PushResult pushResult = send(p.getPushUrl(), JsonUtil.toJson(list));
            result.add(pushResult);
            MessageData messageData = new MessageData();
            messageData.setId(p.getId());
            if (pushResult.status == 0) {
                messageData.setPushStatus(PushStatus.SUCCESS);
            } else {
                messageData.setPushStatus(PushStatus.FAIL);
            }
            messageData.setPushTime(DateUtil.getFormatDate());
            messageDataApi.updatePushStatusAndTime(messageData);
        }
        return result;
    }

    private PushResult send(String uri, String string) throws Exception {
        try {
            String result = reSend(uri, string, 3);
            PushResult pushResult = new PushResult();
            pushResult.message = result;
            if (result.contains("success")) {
                pushResult.status = PushResult.SUCCESS;

            } else {
                pushResult.status = PushResult.FAILED;
            }
            return pushResult;
        } catch (Exception e) {
            PushResult pushResult = new PushResult();
            pushResult.message = "重试三次失败";
            pushResult.status = PushResult.FAILED;
            return pushResult;
        }
    }

    private String reSend(String url, String params, int times) throws Exception {
        System.out.println("巡检推送参数:" + params.substring(0, 300));
        if (times == 0) {
            return sendJson(url, params);
        } else {
            try {
                return sendJson(url, params);
            } catch (Exception e) {
                times -= 1;
                return reSend(url, params, times);
            }
        }
    }

    private String sendJson(String url, String params) throws Exception {
        HttpURLConnection conn = null;
        String resultStr = "";
        try {
            URL u = new URL(url);
            conn = (HttpURLConnection) u.openConnection();
            conn.setConnectTimeout(10000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            conn.setRequestProperty("Accept", "text/plain");
            conn.setRequestMethod("POST");

            PrintWriter writer = new PrintWriter(conn.getOutputStream());
            writer.print(params);
            writer.flush();

            int resp = conn.getResponseCode();
            StringBuilder sbuilder = new StringBuilder();
            if (resp != 200)
                throw new IOException("Response code=" + resp);

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                sbuilder.append(line);
                sbuilder.append("\n");
            }
            resultStr = sbuilder.toString();
        } catch (IOException e) {
            throw e;
        } finally {
            conn.disconnect();
        }
        System.out.println("推送结果：" + resultStr);
        return resultStr;
    }
}
