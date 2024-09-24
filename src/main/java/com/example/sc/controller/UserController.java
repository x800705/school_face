package com.example.sc.controller;


import com.example.sc.entity.User;
import com.example.sc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zls
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 查询单条
     *
     * @param name
     * @return
     */
    @GetMapping("/is_sign/{name}")
    public User is_sign(@PathVariable String name) {

        return userService.is_sign(name);
    }

    @PostMapping("login")
    public User login(String name,String pwd){
        return userService.login(name,pwd);
    }

    @PostMapping("register")
    public int register(String name,String pwd){
        return userService.register(name,pwd);
    }

}
