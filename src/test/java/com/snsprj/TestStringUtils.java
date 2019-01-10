package com.snsprj;

import com.snsprj.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.*;

/**
 * @author SKH
 * @program sbsm
 * @description
 * @date 2018-06-07 14:45
 **/
@Slf4j
public class TestStringUtils {


    @Test
    public void test() {

        log.info("boot");

        List<String> memberList = new ArrayList<>();

        memberList.add("xiao");
        memberList.add("john");
        memberList.add("tony");

        for (String str : memberList) {
            log.info("member is {},{}", str, str);
        }
    }

    @Test
    public void testStringValueOf() {

        List<Object> list = new ArrayList<>();
        list.add("abc");
        list.add(123);
        list.add(100L);
        list.add(new Date());

        for (Object value : list) {
            if (value instanceof Number) {
                System.out.println("====> value is " + String.valueOf(value));
            }

            if (value instanceof String) {
                System.out.println("====> value is " + String.valueOf(value));
            }

            if (value instanceof Date) {
                System.out.println("====> value is " + String.valueOf(value));
            }
        }
    }

    @Test
    public void getListJaon(){
        List<String> nameList = new ArrayList<>();
        nameList.add("张三");
        nameList.add("张三");
        nameList.add("张三");

        Map<String, List<String>> params = new HashMap<>();
        params.put("nameList", nameList);
        String paramJson = JsonUtil.obj2String(params);

        System.out.println(paramJson);

        String [] strArr = new String[3];

        Object object = strArr;

        if (object instanceof List){

        }
    }

    @Test
    public void testStringSplit(){

        String str = "boo|and|foo";

        String[] resultArr = str.split("[|]",7);

        log.info("====>resultArr is {}", resultArr);
    }

    @Test
    public void testDoWhile(){

        int indexA = 10;
        int indexB = 10;
        int indexC = 10;

        do {

            if (indexA <= 0){
                log.info("process is return!");
                return;
            }
            indexA --;
        }while (true);
    }

    /**
     * 测试log4j对异常的打印
     */
    @Test
    public void exceptionTest(){

        int a = 1;
        int b = 0;

        try {
            int c = a/b;
        }catch (Exception e){
//            log.error(e.getMessage() + e);
//            e.printStackTrace();
//            log.error("error is {}", e);
            log.error("error is ", e);
        }
    }

    @Test
    public void test3(){

        String path = "123";
        Map<String, Object> objectMap= new HashMap<>();
        objectMap.put(path, null);

        try {
            long orgId = (long) objectMap.get(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test4(){

        String domain = "DC=SNSPRJ,DC=CN";

        //distinguishedName
//        String path = "CN=张三,OU=食材采购部,OU=后厨部,OU=同福客栈,DC=snsprj,DC=cn";

        String path = "OU=食材采购部,OU=后厨部,OU=同福客栈,DC=snsprj,DC=cn";

        String pathStr = path.substring(0, path.toUpperCase().indexOf("," + domain));
        log.info("====>pathStr is {}", pathStr);


        String baseDN = "OU=同福客栈";
        String[] sp = baseDN.split(",");

        if (pathStr.toUpperCase().contains("CN=")) {
            String[] split = pathStr.split(",");
            for (int i = split.length - sp.length - 1; i > 0; i--) {
                // 如果是吉大ldap过滤"t="字段，否则带"t="使用下面的公共解析方法有错误
                if (!split[i].contains("t=")) {
                    String[] strOu = split[i].split("=");
                    if (strOu.length >= 2) {
                        String orgName = strOu[1];
                        log.info("====>orgName is {}", orgName);
//                        list.add(orgName);
                    }
                }
            }
        }
    }

    @Test
    public void test5(){
        // 1546847008167
        System.out.println(System.currentTimeMillis());

        Map<String,Object> testMap = new HashMap<>();
        testMap.put("key","va;iue");

        log.info("====>map is {}", testMap);
    }


}
