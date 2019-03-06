package com.snsprj.sbsm.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * httpClient工具类
 *
 * @author SKH
 * @date 2018-06-07 14:59
 **/
@Slf4j
public class HttpUtil {



    private static final String utf8 = "utf-8";

    public static String doGet(String url) {
        return doGet(url, null);
    }


    /**
     * httpClient get 请求
     *
     * @param url url
     * @return String
     */
    public static String doGet(String url, Map<String, Object> paramMap) {

        log.info("====> request url is {}, params is {}", url, JsonUtil.obj2String(paramMap));

        try {
            String protocol = StringUtils.substringBefore(url, ":");
            if (StringUtils.equals(protocol, "http")) {
                // http request

                URIBuilder uriBuilder = new URIBuilder(url);
                uriBuilder.setParameters(paramBuilder(paramMap));

                HttpGet httpGet = new HttpGet(uriBuilder.build());

                return execute(httpGet);

            } else {
                // TODO https request
                return null;
            }

        } catch (Exception ex) {

            ex.printStackTrace();
            return null;
        }
    }


    /**
     * httpClient post 请求
     *
     * @param url url
     * @param paramMap 请求参数可以是基本类型的List、基本类型的Array、基本类型
     * @return String
     */
    public static String doPost(String url, Map<String, Object> paramMap) {

        log.info("====>request url is {}, params is {}", url, JsonUtil.obj2String(paramMap));

        Map<String, String> headers = new HashMap<>();

        return doPost(url,headers,paramMap);
    }

    /**
     * application/json
     *
     * @param url url
     * @param object object
     * @param paramMap paramMap 仅支持基本类型
     * @return result
     */
    public static String doPostJson(String url, Object object, Map<String, Object> paramMap) {

        log.info("====>request url is {}, object is {}, params is {}",
                url, object, JsonUtil.obj2String(paramMap));

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json;charset=utf-8");

        String jsonString = JsonUtil.obj2String(object);

        StringEntity stringEntity = new StringEntity(jsonString, utf8);

        // 把paramMap构造成url参数
        StringBuilder paramValue = new StringBuilder();
        if (!StringUtils.contains(url,"?")){
            paramValue = new StringBuilder("?");
        }
        for (Entry<String, Object> entry : paramMap.entrySet()){
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value != null){

                String stringValue = String.valueOf(value);
                try {
                    stringValue = URLEncoder.encode(stringValue, utf8);

                    paramValue.append(key).append("=").append(stringValue).append("&");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }

        // 去除最后一个&符号
        paramValue = new StringBuilder(StringUtils.removeEnd(paramValue.toString(), "&"));

        url += paramValue;

        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(stringEntity);

        // 设置请求头
        httpPost.setHeaders(headerBuilder(headers));

        try {
            return HttpUtil.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 1、spring mvc不支持参数中有多个@RequestBody
     * 2、@RequestBody与@RequestParam共存时，@RequestParam只能是普通类型并且要放到url中
     * @param url
     * @param headers
     * @param paramMap
     * @return
     */
    private static String doPost(String url, Map<String, String> headers,
            Map<String, Object> paramMap) {

        log.info("====>request url is {}, header is {}, params is {}", url,
                JsonUtil.obj2String(headers), JsonUtil.obj2String(paramMap));

        try {
            UrlEncodedFormEntity urlEncodedFormEntity =
                    new UrlEncodedFormEntity(paramBuilder(paramMap), utf8);

            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(urlEncodedFormEntity);

            // 设置请求头
            httpPost.setHeaders(headerBuilder(headers));

            return HttpUtil.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 构造http请求参数，Map中可以有基本类型的List、基本类型的Array、基本类型、类对象
     *
     * @param paramMap k/v参数
     */
    private static List<NameValuePair> paramBuilder(Map<String, Object> paramMap) {

        List<NameValuePair> nvps = new ArrayList<>();

        if (paramMap != null && !paramMap.isEmpty()) {

            for (Entry<String, Object> entry : paramMap.entrySet()) {

                String key = entry.getKey();
                Object value = entry.getValue();

                if (value != null){

                    // 判断是否是数组
                    if (value.getClass().isArray()){

                        Object [] objArr = (Object[]) value;
                        for (Object object : objArr){
                            nvps.add(new BasicNameValuePair(key, String.valueOf(object)));
                        }
                        continue;
                    }

                    // 判断是否是List
                    if (value instanceof List){
                        List valueList = (List) value;
                        for (Object object : valueList){
                            nvps.add(new BasicNameValuePair(key, String.valueOf(object)));
                        }
                        continue;
                    }

                    // 判断是否是基本类型
                    if (value instanceof String || value instanceof Number || value instanceof Date) {

                        nvps.add(new BasicNameValuePair(key, String.valueOf(value)));

                    }

                    // 其他作为json处理
                    String json = JsonUtil.obj2String(value);
                    nvps.add(new BasicNameValuePair(key, json));
                }
            }
        }
        return nvps;
    }


    /**
     * 构造请求头，默认增加Content-Type: application/x-www-form-urlencoded
     *
     * @param headerMap heads
     * @return Header
     */
    private static Header[] headerBuilder(Map<String, String> headerMap) {

        List<Header> headerList = new ArrayList<>();
        if (headerMap == null) {
            headerMap = new HashMap<>();
        }

        if (!headerMap.containsKey("User-Agent")) {
            headerMap.put("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.7.6)");
        }

        if (!headerMap.containsKey("Content-Type")) {
            headerMap.put("Content-Type", "application/x-www-form-urlencoded");
        }

        for (Entry<String, String> entry : headerMap.entrySet()) {

            String key = entry.getKey();
            String value = entry.getValue();

            headerList.add(new BasicHeader(key, value));
        }

        return headerList.toArray(new Header[headerList.size()]);
    }

    /**
     * @param request HttpUriRequest,get/post
     * @return String
     * @throws IOException IOException
     */
    private static String execute(HttpUriRequest request) throws IOException {

        String result = "";

        CloseableHttpClient httpclient = HttpClients.createDefault();

        try (CloseableHttpResponse response = httpclient.execute(request)) {

            log.info("====>request code is {}", response.getStatusLine().toString());

            HttpEntity entity = response.getEntity();

            if (entity != null) {
                result = EntityUtils.toString(entity, utf8);
                log.info("====> result is {}", result);
            }
            // do something useful with the response body
            // and ensure it is fully consumed
            EntityUtils.consume(entity);
        }
        return result;
    }
}
