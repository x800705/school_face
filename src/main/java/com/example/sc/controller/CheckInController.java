package com.example.sc.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.sc.entity.CheckIn;
import com.example.sc.entity.Student;
import com.example.sc.mapper.CheckInMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/checkin")
public class CheckInController {

    @Autowired
    CheckInMapper checkInMapper;

    @PostMapping("/page")
    public Page<CheckIn> page(@RequestBody CheckIn checkIn){


        // 分页查询参数
        Page<CheckIn> pages = Page.of(checkIn.getPage(),checkIn.getSize());

        // 导入条件
        LambdaQueryWrapper<CheckIn> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(checkIn.getState() != 10,CheckIn::getState,checkIn.getState());
        queryWrapper.eq(CheckIn::getClasses,checkIn.getClasses());


        queryWrapper.orderByDesc(CheckIn::getTime);

        //进行查询
        Page<CheckIn> object = checkInMapper.selectPage(pages,queryWrapper);
        return object;


    }



}
