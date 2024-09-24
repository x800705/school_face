package com.example.sc.controller;

import com.example.sc.entity.sm;
import com.example.sc.mapper.SmMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sm")
public class SmController {
    @Autowired
    SmMapper smMapper;

    @GetMapping("/get")
    public List<sm> get(){
        return smMapper.find();
    }
}
