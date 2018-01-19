package com.geekzhang.demo.controller.api;

import com.geekzhang.demo.orm.User;
import com.geekzhang.demo.redis.RedisClient;
import com.geekzhang.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    RedisClient redisClient;

    @RequestMapping(value = "/login", method = {RequestMethod.GET})
    public Map login(User user){
        Map<String, Object> map = userService.login(user);
        return map;
    }

    @RequestMapping("/token")
    public String redis(){
        return "success";
    }
}
