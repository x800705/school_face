package com.example.sc.controller;

import com.example.sc.entity.Admin;
import com.example.sc.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Repository
public class TextContoller {
    @Autowired
    TestMapper testMapper;


    @GetMapping("/test")
    public List<Admin> test(Admin admin){
        return testMapper.find(admin);
    }


}
