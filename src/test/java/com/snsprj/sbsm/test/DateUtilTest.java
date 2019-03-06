package com.snsprj.sbsm.test;

import com.snsprj.sbsm.utils.DateUtil;
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
}
