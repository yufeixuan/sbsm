package com.snsprj.controller;

import com.snsprj.common.ServerResponse;
import com.snsprj.model.User;
import com.snsprj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/user/create")
    @ResponseBody
    public ServerResponse createUser(){

        User user = new User();
        user.setAccount("123");

        return userService.createUser(user);
    }
}
