package com.snsprj.sbsm.test;

import com.snsprj.sbsm.utils.DateUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class DateUtilTest {

    @Test
    public void testGetDayTimeLeft() {
        int timeLeft = DateUtil.getDayTimeLeft();
        float hourLeft = (float) timeLeft / 60 / 60;
        log.info("====>hour left is {}", hourLeft);
    }

    @Test
    public void test16() {
        int i = 0xaa;
        Map<String, Object> map = new HashMap<>();
        map.put("key", i);

        String str = String.valueOf(map.get("key"));
        byte[] bytes = str.getBytes();
        log.info("===>str is {}", str);
    }


    /**
     * 获取距离现在 timestamp 的时间
     *
     * @param timestamp 时间戳，13位
     * @return String
     */
    private String getHistoryTime(Long timestamp) {

        Long nowTimestamp = System.currentTimeMillis();
        Long historyTimestamp = nowTimestamp - timestamp;

        Date historyDate = new Date(historyTimestamp);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(historyDate);
    }

    @Test
    public void getHistoryTimeTest(){

        String historyTime = getHistoryTime(6 * 30 * 24 * 60 * 60 * 1000L);
        System.out.println(historyTime);
    }
}
