package com.snsprj.utils;

import java.util.UUID;
import org.apache.commons.lang3.StringUtils;

/**
 * 获取uuid
 *
 * @author SKH
 * @date 2018-10-16 18:22
 **/
public class UUIDUtil {

    /**
     * 获取32位的uuid
     *
     * @return String
     */
    public static String getUUID() {

        UUID uuid = UUID.randomUUID();

        // 去掉"-"符号  
        return StringUtils.remove(uuid.toString(),"-");
    }
}
