package com.snsprj.sbsm.service;

import com.snsprj.sbsm.common.ServerResponse;
import com.snsprj.sbsm.vo.UserVo;

public interface UserService {

    /**
     * 创建用户
     * @param userVo
     */
    ServerResponse createUser(UserVo userVo);
}
