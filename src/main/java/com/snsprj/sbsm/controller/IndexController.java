package com.snsprj.sbsm.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/index")
public class IndexController {

    @RequestMapping(value = "/hello/{name}", method = {RequestMethod.GET,RequestMethod.POST})
    public String index(@PathVariable("name") String displayName){

        return "Hello " + displayName;
    }
}
