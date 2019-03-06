package com.snsprj.sbsm.service;

import com.snsprj.sbsm.mapper.UserMapper;
import com.snsprj.sbsm.model.User;
import com.snsprj.sbsm.model.UserExample;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
    public void userCreateTest(){

        String account = "😭😄🍌";
        User user = new User();
        user.setAccount(account);
        user.setPassword("123456");
        user.setSalt("salt");

        userService.createUser(user);

        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountEqualTo(account);
        List<User> userList = userMapper.selectByExample(userExample);

        log.info("userAccount is {}", userList.get(0).getAccount());

        Assert.assertEquals(1, userList.size());
    }

    @Test
//    @Transactional
    public void test(){

        String account = "😭😄🍌";

        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountEqualTo(account);
        List<User> userList = userMapper.selectByExample(userExample);

        log.info("userAccount is {}", userList.get(0).getAccount());

        Assert.assertEquals(1, userList.size());
    }

}
