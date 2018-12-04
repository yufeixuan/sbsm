package com.snsprj;

import com.snsprj.model.UserInfo;
import com.snsprj.utils.HttpUtil;
import com.snsprj.utils.JsonUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author SKH
 * @date 2018-08-27 14:17
 **/
public class TestHttpClient {

    private Logger logger = LoggerFactory.getLogger(TestHttpClient.class);

    @Test
    public void testGet() {

        String url = "http://localhost:8080/test/get";

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", "哈哈");
        paramMap.put("sex", "男");
        HttpUtil.doGet(url, paramMap);
    }



    /**
     * 单个对象；普通参数
     */
    @Test
    public void testPost1() {

        UserInfo userInfo = new UserInfo();
        userInfo.setNickname("baoge");

        String userInfoJson = JsonUtil.obj2String(userInfo);

        logger.info("====> userInfoJson is {}", userInfoJson);

        String url = "http://localhost:8070/test/post1";

        Map<String, Object> paramMap = new HashMap<>();

//        paramMap.put("UserInfo", userInfo);
        paramMap.put("name", "滑铲");

        HttpUtil.doPostJson(url,userInfo, paramMap);
    }

    @Test
    public void testPost2(){

        List<String> nameList = new ArrayList<>();
        nameList.add("张三");
        nameList.add("李四");
        nameList.add("王五");

        Integer classId = 5;

        Integer [] ageList = new Integer[3];
        ageList[0] = 0;
        ageList[1] = 1;
        ageList[2] = 2;

        Map<String, Object> params = new HashMap<>();
        params.put("nameList", nameList);
        params.put("classId", classId);
        params.put("ageList", ageList);

        String url = "http://localhost:8070/test/post2";

        String result = HttpUtil.doPost(url, params);

        System.out.println(result);

    }
}
