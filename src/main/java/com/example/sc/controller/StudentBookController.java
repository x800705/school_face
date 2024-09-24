package com.example.sc.controller;

import com.alibaba.fastjson.JSON;
import com.example.sc.entity.StuBook;
import com.example.sc.mapper.StudentBookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin

@RequestMapping("stubook")
public class StudentBookController {

    @Autowired
    private StudentBookMapper studentBookMapper;

    @PostMapping()
    public StuBook find(String id){

        return studentBookMapper.find(id);

    }



}
