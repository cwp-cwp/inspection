package com.puzek.platform.inspection.serviceImpl;

import com.cwp.cloud.api.CheckApi;
import com.cwp.cloud.api.UserApi;
import com.cwp.cloud.bean.OrderBy;
import com.cwp.cloud.bean.PageResult;
import com.cwp.cloud.bean.user.ModifyResults;
import com.cwp.cloud.bean.user.TaskResult;
import com.cwp.cloud.bean.user.User;
import com.puzek.platform.inspection.common.Msg;
import com.puzek.platform.inspection.service.CheckService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CheckServiceImpl implements CheckService {
    private final CheckApi checkApi;
    private final UserApi userApi;
    private final static Logger LOG = LoggerFactory.getLogger(CheckServiceImpl.class);

    @Autowired
    public CheckServiceImpl(CheckApi checkApi, UserApi userApi) {
        this.checkApi = checkApi;
        this.userApi = userApi;
    }

    @Override
    public Msg getOneCheckInfo(String timeValue) {
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userApi.login(currentUserName);
        if (user == null) {
            return Msg.failed("请重新登录");
        }
        if (timeValue == null || timeValue.isEmpty()) {
            return Msg.success().add("data", checkApi.getMessageDataByUserId(user.getId(), OrderBy.ASC));
        } else {
            if ("true".equals(timeValue)) {
                return Msg.success().add("data", checkApi.getMessageDataByUserId(user.getId(), OrderBy.ASC));
            } else {
                return Msg.success().add("data", checkApi.getMessageDataByUserId(user.getId(), OrderBy.DESC));
            }
        }
    }

    @Override
    public Msg checkInfo(String batchNumber, String parkNumber, String newCarNumber) {
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userApi.login(currentUserName);
        if (user == null) {
            return Msg.failed("请重新登录");
        }
        boolean result = checkApi.modifyCarNumber(user.getId(), newCarNumber, batchNumber, parkNumber);
        if (result) {
            return Msg.success("修改成功");
        } else {
            return Msg.failed("修改失败");
        }
    }

    @Override
    public Msg getCheckInfoList(Integer userId, String startTime, String endTime, int page, int pageSize) {
        if (userId == -1) {
            String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userApi.login(currentUserName);
            if (user == null) {
                return Msg.failed("请重新登录");
            }
            userId = user.getId();
        }
        LOG.info("用户id：" + userId);
        PageResult<TaskResult> pageResult = checkApi.getTaskResult(userId, startTime, endTime, page, pageSize);
        if (pageResult == null) {
            return Msg.failed("Api返回null");
        } else {
            return Msg.success().add("data", pageResult);
        }
    }

    @Override
    public Msg getErrorCheckInfoList(Integer userId, String startTime, String endTime, int page, int pageSize) {
        PageResult<ModifyResults> pageResult = checkApi.getModifyResults(userId, startTime, endTime, page, pageSize);
        if (pageResult == null) {
            return Msg.failed("Api返回null");
        } else {
            return Msg.success().add("data", pageResult);
        }
    }

    @Override
    public Msg getOneDayChetCount() {
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userApi.login(currentUserName);
        if (user == null) {
            return Msg.failed("请重新登录");
        }
        Integer userId = user.getId();
        Map<String, String> resultMap = checkApi.getOneDayChetCount(userId);
        if (resultMap == null) {
            return Msg.failed("Api 返回null");
        }
        return Msg.success().add("data", resultMap);
    }
}
