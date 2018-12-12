package com.snsprj.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import java.text.SimpleDateFormat;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * json 工具类
 *
 * @author SKH
 * @date 2018-06-13 09:40
 **/
@Slf4j
public class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {

        // 序列化--对象的所有字段全部列入
        objectMapper.setSerializationInclusion(Include.ALWAYS);

        // 序列化--取消默认转换timestamps形式
        objectMapper.configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false);

        // 序列化--统一日期格式
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        // 序列化--忽略空bean转json的错误
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        // 反序列化--忽略在json中存在，但是在java对象中不存在对应属性的情况。
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }


    /**
     * 对象序列化到json字符串
     *
     * @param obj 需要序列化的对象
     * @return json字符串
     */
    public static <T> String obj2String(T obj) {

        if (obj == null) {
            return null;
        }

        try {
            return obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * json字符串反序列化成对象
     *
     * @param str json字符串
     * @param typeReference ex,new TypeReference<List<String>>(){}
     * @return T
     */
    public static <T> T string2Obj(String str, TypeReference<T> typeReference) {

        if (StringUtils.isBlank(str) || typeReference == null) {
            return null;
        }

        try {
            return objectMapper.readValue(str, typeReference);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * json字符串反序列化成对象
     *
     * @param str json字符串
     * @param collectionClass 集合类型
     * @param elementClasses 集合中元素类型
     * @return T
     */
    public static <T> T string2Obj(String str, Class<?> collectionClass,
            Class<?>... elementClasses) {

        JavaType javaType = objectMapper.getTypeFactory()
                .constructParametricType(collectionClass, elementClasses);

        try {
            return objectMapper.readValue(str, javaType);
        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }
    }

}
