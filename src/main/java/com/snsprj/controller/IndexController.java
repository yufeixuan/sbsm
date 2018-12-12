package com.snsprj.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/index/")
@Slf4j
public class IndexController {

    @RequestMapping(value = "/hello/{name}", method = {RequestMethod.GET, RequestMethod.POST})
    public String index(HttpServletRequest request, @PathVariable("name") String displayName) {

        HttpSession session = request.getSession();

        Cookie[] cookies = request.getCookies();

        if (cookies == null){
            log.error("====>cookie is null!");
        }else {
            log.info("====>get cookie success!");
            for (Cookie cookie : cookies){
                log.info("====> cookie name is {}, value is {}",cookie.getName(), cookie.getValue());
            }
        }

        Integer number;
        if (session == null){
            return "session is null!!";
        }else{
            number = (Integer) session.getAttribute("number");

            if (number == null){
                number = 1;
            }else {
                number ++;
            }
            session.setAttribute("number",number);
        }

        log.info("===>username is {}, sex is {}, age is {}","xiao","nan",28);
        return "Hello " + number;
    }




}
