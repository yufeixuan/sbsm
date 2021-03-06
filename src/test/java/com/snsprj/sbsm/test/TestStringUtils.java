package com.snsprj.sbsm.test;

import com.snsprj.sbsm.utils.JsonUtil;
import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
    public void getListJaon() {
        List<String> nameList = new ArrayList<>();
        nameList.add("张三");
        nameList.add("张三");
        nameList.add("张三");

        Map<String, List<String>> params = new HashMap<>();
        params.put("nameList", nameList);
        String paramJson = JsonUtil.obj2String(params);

        System.out.println(paramJson);

        String[] strArr = new String[3];

        Object object = strArr;

        if (object instanceof List) {

        }
    }

    @Test
    public void testStringSplit() {

        String str = "boo|and|foo";

        String[] resultArr = str.split("[|]", 7);

        log.info("====>resultArr is {}", resultArr);
    }

    @Test
    public void testDoWhile() {

        int indexA = 10;
        int indexB = 10;
        int indexC = 10;

        do {

            if (indexA <= 0) {
                log.info("process is return!");
                return;
            }
            indexA--;
        } while (true);
    }

    /**
     * 测试log4j对异常的打印
     */
    @Test
    public void exceptionTest() {

        int a = 1;
        int b = 0;

        try {
            int c = a / b;
        } catch (Exception e) {
//            log.error(e.getMessage() + e);
//            e.printStackTrace();
//            log.error("error is {}", e);
            log.error("error is ", e);
        }
    }

    @Test
    public void test3() {

        String path = "123";
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put(path, null);

        try {
            long orgId = (long) objectMap.get(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test4() {

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
    public void test5() {
        // 1546847008167
        System.out.println(System.currentTimeMillis());

        Map<String, Object> testMap = new HashMap<>();
        testMap.put("key", "va;iue");

        log.info("====>map is {}", testMap);
    }


    @Test
    public void emojiTest() {

        String pureStr = "你哈，:joy::cry:";
        String pureStrPare2Unicode = EmojiParser.parseToUnicode(pureStr);
        log.info("{}", pureStrPare2Unicode);
    }

    /**
     * 获取当天还剩余多少秒
     */
    @Test
    public void dayLeftSecondsTest() {

        LocalDateTime midnight = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        long millSeconds = ChronoUnit.MILLIS.between(LocalDateTime.now(), midnight);
        int seconds = (int) ChronoUnit.SECONDS.between(LocalDateTime.now(), midnight);
        System.out.println("当天剩余毫秒：" + millSeconds);
        System.out.println("当天剩余秒：" + seconds);

    }

    @Test
    public void testSubString() {

//        String str = "CN=吕轻侯,OU=财务部,OU=同福客栈,DC=snsprj,DC=cn";
        String str = "CN=张三,OU=食材采购部,OU=后厨部,OU=同福客栈,DC=snsprj,DC=cn";

        String[] tempArr = StringUtils.split(str, ",");
        int arrLength = tempArr.length;

        String entryDn = tempArr[arrLength - 2].split("=")[1] + "." + tempArr[arrLength - 1].split("=")[1];

        log.info("====>entryDn is {}", entryDn);
    }

    @Test
    public void testByte() {
        String str = "TenancyInfoCache";
        byte[] rawBytes = str.getBytes();
        System.out.println(rawBytes);
    }

    @Test
    public void testListClear() {

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(12);
        list.add(13);
        list.add(14);

        log.info("list is {}", list);

        list.clear();

        log.info("list is {}", list);
    }

    @Test
    public void testSplit() {

        String str1 = "000-";
        String str2 = "000--222";

        String str3 = "0000000000000000001-716120294369742848-716120294382325760-";

        String str4 = "";
        String[] arr3 = str3.split("-");
        String[] arr4 = str4.split("-");

        log.info("");
    }

    @Test
    public void testList2Array() {

        List<String> list = new ArrayList<>();
        list.add("zhangsan");

//        String[] arr = (String[]) list.toArray();

        String[] arrResult = new String[list.size()];
        String[] arr = list.toArray(arrResult);

        log.info("====> arr is {}", arr);
    }

    @Test
    public void testSetAddAll(){

        List<Long> list0 = new ArrayList<>();
        list0.add(1L);
        list0.add(1L);
        list0.add(2L);
        list0.add(3L);
        list0.remove(3L);

        List<Long> list = new ArrayList<>();
        list.add(1L);
        list.add(6L);
        list.add(4L);
        list.add(3L);

        HashSet<Long> set = new HashSet<>();
        set.addAll(list0);
        set.addAll(list);

        System.out.println("set size is: " + set.size());
        System.out.println(set);

        List<Long> list2 = new ArrayList<>();
        list2.add(3L);
        list2.add(4L);
        set.removeAll(list2);
        System.out.println("set size is: " + set.size());
        System.out.println(set);
    }


    @Test
    public void testCompareVersion(){

        String sourceVersion = "29";
        String targetVersion = "31";
        int ret = this.compareVersionCode(sourceVersion, targetVersion);
        log.info("");
    }
    /**
     * 比较两个应用的VersionCode
     * @param sourceVersion
     * @param targetVersion
     * @return 1代表source>target -1代表source<target 0代表source=target
     */
    private int compareVersionCode(String sourceVersion, String targetVersion) {
        if (sourceVersion.contains(".")) {
            String[] vs1 = sourceVersion.split("[.]");
            String[] vs2 = targetVersion.split("[.]");
            if (vs1.length > vs2.length) {
                for (int i = 0; i < vs2.length; i++) {
                    if (Math.subtractExact(Integer.valueOf(vs1[i]), Integer.valueOf(vs2[i])) > 0) {
                        return 1;
                    } else if (Math.subtractExact(Integer.valueOf(vs1[i]), Integer.valueOf(vs2[i])) < 0) {
                        return -1;
                    }
                }
                //vs1: 3.3.0   vs2:  3.3
                if (Integer.valueOf(vs1[vs2.length]) != 0){
                    return 1;
                }
            }else if (vs1.length == vs2.length){
                for (int i = 0; i < vs1.length; i++) {
                    if (Math.subtractExact(Integer.valueOf(vs1[i]), Integer.valueOf(vs2[i])) > 0) {
                        return 1;
                    } else if (Math.subtractExact(Integer.valueOf(vs1[i]), Integer.valueOf(vs2[i])) < 0) {
                        return -1;
                    }
                }
            }else if (vs1.length < vs2.length){
                for (int i = 0; i < vs1.length; i++) {
                    if (Math.subtractExact(Integer.valueOf(vs1[i]), Integer.valueOf(vs2[i])) > 0) {
                        return 1;
                    } else if (Math.subtractExact(Integer.valueOf(vs1[i]), Integer.valueOf(vs2[i])) < 0) {
                        return -1;
                    }
                }
                //vs1: 3.3.0   vs2:  3.3
                if (Integer.valueOf(vs2[vs1.length]) != 0){
                    return -1;
                }
            }
        }else {
            int vs1 = Integer.valueOf(sourceVersion);
            int vs2 = Integer.valueOf(targetVersion);
            return vs1-vs2;
        }
        return 0;
    }

    @Test
    public void testDeleteCharAt(){
        StringBuilder str = new StringBuilder("sdf");

        str.deleteCharAt(str.length()-1);

        System.out.println("--------------" + str);

    }

}
