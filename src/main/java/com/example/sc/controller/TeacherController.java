package com.example.sc.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.sc.entity.Business;
import com.example.sc.entity.Food;
import com.example.sc.mapper.TeacherMapper;
import com.example.sc.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import com.example.sc.entity.Teacher;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    TeacherService teacherService;

    @Autowired
    TeacherMapper teacherMapper;

    @Autowired
    private RedisTemplate redisTemplate;


    //分页查询
    @GetMapping("/get_all")
    public Page<Teacher> get_all(Teacher teacher,int page, int size){
        Page<Teacher> pages = Page.of(page,size);

        LambdaQueryWrapper<Teacher> lqw = new LambdaQueryWrapper<>();


        lqw.eq(teacher.getName() != null,Teacher::getName,teacher.getName());
        lqw.eq(teacher.getId() != null,Teacher::getId,teacher.getId());


        return teacherMapper.selectPage(pages,lqw);
    }





    @PostMapping("/add")
    public int add(@RequestBody Teacher teacher){
        teacherMapper.create_money(teacher.getId());
        return teacherMapper.insert(teacher);
    }

    @PostMapping("/delete")
    public int del(@RequestBody Teacher teacher){
        System.out.println(teacher.getId());
        String paths = "D:/JavaWeb/sc/src/main/resources/static/";
        File file = new File(paths + teacher.getImgSrc());
        if(file.delete()) System.out.println("删除成功");
        return teacherMapper.deleteById(teacher.getId());
    }

    @PostMapping("/update")
    public int update(@RequestBody Teacher teacher){
        return teacherMapper.updateById(teacher);
    }


    //清理缓存
    @GetMapping("/clean")
    public void clean(){


        List<Teacher> list = teacherMapper.find_imgsrc();
        List<String> new_list = new ArrayList<>();
        for(int i = 0 ;i<list.size();i++){
            new_list.add(list.get(i).getImgSrc());

        }

        System.out.println(new_list);


        File file = new File("D:\\JavaWeb\\sc\\src\\main\\resources\\static\\teacher");
        File[] files = file.listFiles();
        System.out.println(files);

        for(File file2 : files){
            System.out.println(file2.getName());
            String name = "teacher/" + file2.getName();
            System.out.println(name);
            System.out.println(new_list.contains(name));
            if(!new_list.contains(name)){
                file2.delete();
            }

        }





        System.out.println(new_list);

    }


    //id密码登录
    @PostMapping("/id_login")
    public Teacher login(@RequestBody Teacher teacher) {
        LambdaQueryWrapper<Teacher> lqw = new LambdaQueryWrapper<>();

        lqw.eq(Teacher::getId, teacher.getId());
        lqw.eq(Teacher::getPwd, teacher.getPwd());

        return teacherMapper.selectOne(lqw);
    }


    //获取验证码
    @GetMapping("/get_code")
    public String get_core(String phone) {

        //如果已经有验证码了,删除再重新生成
        if (redisTemplate.hasKey(phone)) {
            redisTemplate.delete(phone);
        }

        //随机生成4位验证码
        String code = String.valueOf((int) ((Math.random() * 9 + 1) * 1000));

        //将验证码放入Redis
        redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);

        try {
            //发送短信
            //TextConfig textConfig = new TextConfig(phone,code);
            return "success";


        } catch (Exception e) {
            e.printStackTrace();
            //发送短信失败
            return "error";
        }



    }


    //手机验证码登录
    @GetMapping("/phone_login")
    public Object phone_login(String phone, String code) {

        //获取redis的验证码
        String c_code = (String) redisTemplate.opsForValue().get(phone);
        System.out.println(c_code);


        //判断是否验证码正确
        if (Objects.equals(c_code, code)) {
            LambdaQueryWrapper<Teacher> lqw = new LambdaQueryWrapper<>();
            lqw.eq(Teacher::getPhone, phone);
            //判断是否数据库存在有电话的用户
            System.out.println(lqw);
            if (teacherMapper.selectOne(lqw) != null) return teacherMapper.selectOne(lqw);
            else return "no_phone";
        } else {
            return "error_code";
        }


    }















}
