package com.puzek.platform.inspection.service;

import com.puzek.platform.inspection.common.Msg;

public interface PushService {
    Msg doPush(String batchNumber);
}
