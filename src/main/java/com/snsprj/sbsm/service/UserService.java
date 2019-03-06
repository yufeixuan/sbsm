package com.snsprj.sbsm.service;

import com.snsprj.sbsm.common.ServerResponse;
import com.snsprj.sbsm.model.User;

public interface UserService {

    /**
     * 创建用户
     * @param user
     */
    ServerResponse createUser(User user);
}
