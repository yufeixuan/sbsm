package com.snsprj.service.impl;

import com.snsprj.common.ServerResponse;
import com.snsprj.mapper.UserMapper;
import com.snsprj.model.User;
import com.snsprj.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponse createUser(User user) {

        logger.info("====>insert user, user account is {}", user.getAccount());

        userMapper.insert(user);

        return ServerResponse.createBySuccess();
    }
}
