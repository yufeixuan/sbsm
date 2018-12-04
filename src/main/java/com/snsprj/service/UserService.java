package com.snsprj.service;

import com.snsprj.common.ServerResponse;
import com.snsprj.model.User;

public interface UserService {

    /**
     * 创建用户
     * @param user
     */
    ServerResponse createUser(User user);
}
