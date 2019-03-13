package com.snsprj.sbsm.controller;

import com.snsprj.sbsm.common.ServerResponse;
import com.snsprj.sbsm.form.UserForm;
import com.snsprj.sbsm.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 新建用户
     *
     * @param userForm userForm
     * @return ServerResponse
     */
    @PostMapping("/user/create")
    public ServerResponse createUser(@RequestBody @Validated UserForm userForm) {

        log.info("====>");

        return userService.createUser(userForm);
    }
}
