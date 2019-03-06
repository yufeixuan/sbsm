package com.snsprj.sbsm;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@SpringBootApplication
@MapperScan("com.snsprj.sbsm.mapper")
public class SbsmApplication extends SpringBootServletInitializer {

    // war包
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SbsmApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(SbsmApplication.class, args);
    }

    /**
     * 设置RedisTemplate序列化方式为jackson2JsonRedisSerializer
     *
     * @param redisConnectionFactory RedisConnectionFactory
     * @return RedisTemplate
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {

        // 1.创建redisTemplate 模板
        RedisTemplate<String, Object> template = new RedisTemplate<>();

        // 2.关联redisConnectionFactory
        template.setConnectionFactory(redisConnectionFactory);

        // 3.创建序列化类
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();

        // 4.设置可见度
        objectMapper.setVisibility(PropertyAccessor.ALL, Visibility.ANY);

        // 5.启动的默认类型
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

        // 6.序列化类，对象映射设置
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        // 7.设置value的转化格式和key的转化格式
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }
}
