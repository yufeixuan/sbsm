package com.snsprj.sbsm.controller;

import com.snsprj.sbsm.common.ServerResponse;
import com.snsprj.sbsm.vo.UserVo;
import com.snsprj.sbsm.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 新建用户
     *
     * @param userVo content-type:application/json
     * @return ServerResponse
     */
    @PostMapping("/user/create")
    public ServerResponse createUser(@RequestBody @Validated UserVo userVo) {

        log.info("====>");

        return userService.createUser(userVo);
    }
}
