package com.snsprj.service;

import com.snsprj.mapper.UserMapper;
import com.snsprj.model.User;
import com.snsprj.model.UserExample;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Test
    @Transactional
    public void userCreateTest(){

        String account = "";
        User user = new User();
        user.setAccount(account);

        userService.createUser(user);

        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountEqualTo(account);
        List<User> userList = userMapper.selectByExample(userExample);

        Assert.assertEquals(1, userList.size());
    }

}
