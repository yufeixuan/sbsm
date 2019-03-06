package com.snsprj.sbsm.utils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * 时间工具类
 */
public class DateUtil {

    /**
     * 获取当天还剩余多少秒
     *
     * @return second
     */
    public static int getDayTimeLeft(){

        LocalDateTime midnight = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        return (int) ChronoUnit.SECONDS.between(LocalDateTime.now(), midnight);
    }
}
