package com.example.sc.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.sc.entity.Business;
import com.example.sc.entity.Teacher;
import com.example.sc.mapper.BusinessMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin
@RequestMapping("/business")
public class BusinessController {

    @Autowired
    private BusinessMapper businessMapper;

    @Autowired
    private RedisTemplate redisTemplate;




    /*
      输入:Business的id和密码
      返回:符合要求的Business


     */

    //id密码登录
    @GetMapping("/id_login")
    public Business login(Business business) {
        LambdaQueryWrapper<Business> lqw = new LambdaQueryWrapper<>();

        lqw.eq(Business::getId, business.getId());
        lqw.eq(Business::getPwd, business.getPwd());

        return businessMapper.selectOne(lqw);
    }


    //获取验证码
    @PostMapping("/get_code")
    public String get_core(String phone) {


        //select * from business where phone = #{phone}


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
    @PostMapping("/phone_login")
    public Object phone_login(String phone, String code) {

        //获取redis的验证码
        String c_code = (String) redisTemplate.opsForValue().get(phone);
        System.out.println(c_code);


            //判断是否验证码正确
            if (Objects.equals(c_code, code)) {
                LambdaQueryWrapper<Business> lqw = new LambdaQueryWrapper<>();
                lqw.eq(Business::getPhone, phone);
                //判断是否数据库存在有电话的用户
                if (businessMapper.selectOne(lqw) != null) return businessMapper.selectOne(lqw);
                else{
                    /**
                     * 根据时间戳生成唯一id
                     */
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSSS");
                        String id = sdf.format(System.currentTimeMillis());
                        Business business = new Business();
                        business.setId(id);
                        business.setPhone(phone);
                        businessMapper.insert(business);
                }
            }

          return "wrong";

    }

    //更改手机号
    @PostMapping("change_phone")
    public String change_phone(Business business,String code){
        String c_code = (String) redisTemplate.opsForValue().get(business.getPhone());

        if(Objects.equals(c_code, code)){
            businessMapper.updateById(business);
            return "1";
        }
        else{
            return "0";
        }

    }

    @PostMapping("change")
    public int change(@RequestBody Business business){
        return businessMapper.updateById(business);
    }

    @GetMapping("getOne")
    public Business getOne(String id){
        return businessMapper.selectById(id);
    }


    //分页查询
    @PostMapping("/page")
    public Page<Business> page(int page, int size){
        Page<Business> pages = Page.of(page,size);
        return businessMapper.selectPage(pages,null);
    }

    //分页查询
    @PostMapping("/del")
    public int del(@RequestBody Business business){

       return businessMapper.deleteById(business.getId());
    }

    @PostMapping("/add")
    public int add(Business business){
        return businessMapper.insert(business);
    }

    @PostMapping("/reset")
    public int reset(@RequestBody Business business){
        return businessMapper.updateById(business);
    }








}
