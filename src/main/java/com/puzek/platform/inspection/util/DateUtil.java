package com.puzek.platform.inspection.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static String getFormatDate() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }
}
