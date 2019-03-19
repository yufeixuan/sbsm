package com.snsprj.sbsm.service;

import com.snsprj.sbsm.mapper.UserMapper;
import com.snsprj.sbsm.model.User;
import com.snsprj.sbsm.model.UserExample;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Test
//    @Transactional
    public void userCreateTest() {

        String account = "😭😄🍌";
        User user = new User();
        user.setAccount(account);
        user.setPassword("123456");
        user.setSalt("salt");

//        userService.createUser(user);

        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountEqualTo(account);
        List<User> userList = userMapper.selectByExample(userExample);

        log.info("userAccount is {}", userList.get(0).getAccount());

        Assert.assertEquals(1, userList.size());
    }

    @Test
//    @Transactional
    public void test() {

        String account = "😭😄🍌";

        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountEqualTo(account);
        List<User> userList = userMapper.selectByExample(userExample);

        log.info("userAccount is {}", userList.get(0).getAccount());

        Assert.assertEquals(1, userList.size());
    }


    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Test
//    @Transactional
    public void testBathInsert() {

        String sql = "INSERT INTO user_info(id, user_id, nickname, avatar, created_at ,updated_at) "
            + "VALUES (:id,:userId,:nickname,:avatar,now(),now())";
        Map<String, Object>[] valueArr = new Map[5];
        for (int index = 0; index < 5; index ++){
            Map<String, Object> valueMap = new HashMap<>();
            valueMap.put("id", index + 1);
            valueMap.put("userId", index + 2);
            valueMap.put("nickname", "nickname");
            valueMap.put("avatar", "avatar");
            valueArr[index] = valueMap;
        }

        int[] updateCountArr = jdbcTemplate.batchUpdate(sql, valueArr);
        log.info("====>updateCountArr is {}", updateCountArr);
    }

    @Test
    public void testBathDelete(){

        String sql = "DELETE FROM user_info WHERE user_id= :userId AND  id = :id";
        Map<String, Object>[] valueArr = new Map[5];
        for (int index = 0; index < 5; index ++){
            Map<String, Object> valueMap = new HashMap<>();
            valueMap.put("id", index + 1);
            valueMap.put("userId", index + 2);
            valueArr[index] = valueMap;
        }

        int[] updateCountArr = jdbcTemplate.batchUpdate(sql, valueArr);
        log.info("====>updateCountArr is {}", updateCountArr);
    }
}
