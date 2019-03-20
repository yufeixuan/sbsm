package com.snsprj.sbsm.controller;

import com.snsprj.sbsm.common.ServerResponse;
import com.snsprj.sbsm.model.UserInfo;
import com.snsprj.sbsm.service.AsyncService;
import com.snsprj.sbsm.service.TestService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author SKH
 * @date 2018-08-27 11:30
 **/
@Controller
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Autowired
    private AsyncService asyncService;

    @Autowired
    private TestService testService;

    @RequestMapping("/get")
    @ResponseBody
    public String testGet(HttpServletRequest request,
            @RequestParam("name") String name) {

        String sex = request.getParameter("sex");
        log.info("====>name is {}, sex is {}", name, sex);

        return name;
    }


    /**
     * 单个对象；普通参数
     * contentType: "application/json"
     */
    @RequestMapping(value = "/post1")
    @ResponseBody
    public String testPost(@RequestBody UserInfo userInfo,
            @RequestParam(value = "name", required = false) String username) {

        String nickname = userInfo.getNickname();
        log.info("====>nickname is {}", nickname);

        log.info("====> username is {}", username);

        return "success";
    }

    /**
     * 基本类型的集合；基本类型
     * Content-Type: application/x-www-vo-urlencoded
     *
     * @param nameList nameList
     * @param classId classId
     * @return String
     */
    @RequestMapping(value = "/post2")
    @ResponseBody
    public String testPost(@RequestParam(value = "nameList") List<String> nameList,
            @RequestParam("classId") Integer classId,
            @RequestParam("ageList") Integer[] ageList){

        if (nameList != null){
            for (String name : nameList){
                log.info("====> name is {}", name);
            }
        }

        if (ageList != null){

            for (Integer age : ageList){
                log.info("====> age is {}", age);
            }
        }

        log.info("====>classId is {}", classId);

        return "success";
    }


    /**
     * 测试异步调用
     */
    @RequestMapping("/async")
    @ResponseBody
    public String testAsync() {

        log.info("====>testAsync start");
        asyncService.executeAsync();
        log.info("====>testAsync end");

        return "success";
    }

    /**
     * 测试页面缓存，304
     */
    @RequestMapping("/cache")
    public String testPageCache(HttpServletResponse response) {

        response.setHeader("Cache-Control", "max-age=3600");
        return "index";
    }

    /**
     * 前后端分离，有前端页面控制跳转
     * @return
     */
    @RequestMapping("/redirect")
    @ResponseBody
    public ServerResponse testRedirect() {

        return ServerResponse.createBySuccess();
    }

    @RequestMapping("/index")
    public String indexPage() {

        return "index";
    }

    @RequestMapping("/session")
    @ResponseBody
    public ServerResponse testSpringSession(HttpServletRequest request){

        HttpSession session = request.getSession();

        session.setAttribute("name","ookokoko");
        return ServerResponse.createBySuccess();
    }

    /**
     * 测试redis计数器
     */
    @GetMapping("/redis/decrease")
    @ResponseBody
    public void testRedisDecrease(){

        testService.testRedis();
    }

    /**
     * 测试emoji
     */
    @RequestMapping("/emoji")
    @ResponseBody
    public ServerResponse testEmoji(@RequestParam(value = "emojiStr") String emojiStr){

        log.info("web前端传递的字符串 {}",emojiStr);

        // 把字符串中emoji转义
        String emojiStrParseToAliases = EmojiParser.parseToAliases(emojiStr);
        log.info("====>替换后的字符串 {}",emojiStrParseToAliases);

        String pureStr = "你哈，:joy::cry:";
        String pureStrPare2Unicode = EmojiParser.parseToUnicode(pureStr);
        return ServerResponse.createBySuccess(pureStrPare2Unicode);
    }
}
