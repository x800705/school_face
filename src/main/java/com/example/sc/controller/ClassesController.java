package com.example.sc.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.sc.entity.ClassCheck;
import com.example.sc.entity.Classes;
import com.example.sc.entity.Sick;
import com.example.sc.entity.Student;
import com.example.sc.mapper.ClassesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/classes")
public class ClassesController {

    @Autowired
    ClassesMapper classesMapper;



    @PostMapping("/page")
    public Page<Classes> page(@RequestBody Classes classes){
        Page<Classes> pages = Page.of(classes.getPage(),classes.getSize());

        // 导入条件
        LambdaQueryWrapper<Classes> queryWrapper = new LambdaQueryWrapper<>();
        //queryWrapper.eq(Sick::getAccept,accept);
        //queryWrapper.eq(Sick::getClasses,classes);

        //if(accept == 10) queryWrapper = null;

        //进行查询
        return classesMapper.selectPage(pages,queryWrapper);
    }


    @GetMapping("is_check")
    public List<Classes> is_check(){
        return classesMapper.is_check();
    }

    @PostMapping("change_check")
    public int change_check(String id,String checks){return classesMapper.change_check(id,checks);}

    @PostMapping("add_list")
    public int add_list(String id,String classes){


        //没有班级考勤记录
        if(classesMapper.is_exsit(id).size() == 0){
            List<Student> students = classesMapper.find_student(classes);
            //遍历班级学生数据加入课堂考勤表
            for(int i=0 ; i<students.size() ; i++){
                Student student = students.get(i);
                System.out.println(student.getName());
                classesMapper.add_list(student.getId(),id);
            }
        }


        return 200;


    }

    @GetMapping("/total")
    public int total(String classes_id){
       return classesMapper.total(classes_id);
    }

    @GetMapping("/page_list")
    public List<ClassCheck> page_list(String class_id,String checks,int page,int size){

        page = (page - 1) * size;
        return classesMapper.page_list(class_id,checks,page,size);
    }

    @GetMapping("/page_total")
    public int page_total(String class_id,String checks){
        return classesMapper.page_total(class_id,checks);
    }

    @PostMapping("/add")
    public int add(@RequestBody Classes classes){
        return classesMapper.insert(classes);
    }

    @PostMapping("/stu_check")
    public int stu_check(String class_id,String stu_id,String time){
        return classesMapper.stu_check(class_id,stu_id,time);
    }



    @PostMapping("/del/{id}")
    public int del(@PathVariable String id){
        return classesMapper.del(id);
    }

    @PostMapping("/stu_find")
    public List<ClassCheck> stu_find(String id,int page){
        page = page * 10;
        return classesMapper.stu_find(id,page);}
}


