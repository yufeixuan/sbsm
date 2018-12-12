package com.snsprj.service.impl;

import com.snsprj.common.ServerResponse;
import com.snsprj.mapper.UserMapper;
import com.snsprj.model.User;
import com.snsprj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponse createUser(User user) {

        log.info("====>insert user, user account is {}", user.getAccount());

        userMapper.insert(user);

        return ServerResponse.createBySuccess();
    }
}
