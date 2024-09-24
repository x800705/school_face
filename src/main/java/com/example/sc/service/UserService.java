package com.example.sc.service;


import com.example.sc.entity.User;
import com.example.sc.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zls
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;



    /**
     * 查询单条
     *
     * @param name
     * @return
     */
    public User is_sign(String name) {
        return userMapper.is_sign(name);
    }


    public User login(String name, String pwd) {
        return userMapper.login(name,pwd);
    }


    public int register(String name, String pwd) {
        return userMapper.register(name,pwd);
    }
}
