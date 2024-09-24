package com.example.sc.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.sc.entity.Bill;
import com.example.sc.entity.Sick;
import com.example.sc.entity.Student;
import com.example.sc.mapper.SickMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@CrossOrigin
@RequestMapping("/sick")
public class SickController {

    @Autowired
    SickMapper sickMapper;

    @PostMapping("/page")
    public Page<Sick> page(int accept,String classes,int page,int size){
        Page<Sick> pages = Page.of(page,size);

        // 导入条件
        LambdaQueryWrapper<Sick> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Sick::getAccept,accept);
        queryWrapper.eq(Sick::getClasses,classes);

        queryWrapper.orderByAsc(Sick::getEnd);

        if(accept == 10) queryWrapper = null;

        //进行查询
        return sickMapper.selectPage(pages,queryWrapper);


    }

    @PostMapping("/change")
    public int change(@RequestBody Sick sick){
        return sickMapper.updateById(sick);
    }

    @PostMapping("/del/{id}")
    public int del(@PathVariable String id){
        return sickMapper.deleteById(id);
    }






    }

