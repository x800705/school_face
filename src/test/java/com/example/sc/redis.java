package com.example.sc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;


import javax.annotation.Resource;


public class redis {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void add(){
        redisTemplate.opsForValue().set("num","123");


    }

}
