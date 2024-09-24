package com.example.sc.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.sc.config.TextConfig;
import com.example.sc.entity.Admin;
import com.example.sc.mapper.AdminMapper;
import com.example.sc.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminsController {

    @Autowired
    public AdminService adminservice;


    //Redis
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private HttpSession session;


    //登录
    @PostMapping("/login")
    public Admin login(String id, String pwd){

        return adminservice.login(id,pwd);

    }

    //类_登录
    @PostMapping("/new_login")
    public Admin new_login(@RequestBody Admin admin){
        LambdaQueryWrapper<Admin> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Admin::getId,admin.getId())
                          .eq(Admin::getPwd,admin.getPwd());



        if(adminMapper.selectOne(lambdaQueryWrapper) != null){
            session.setAttribute("kun","123");
            System.out.println(session.getAttribute("kun"));
            System.out.println("我已设置session");
            System.out.println(admin.getId());
        }

        return adminMapper.selectOne(lambdaQueryWrapper);


    }



    @GetMapping("/search")
    public List<Admin> search_stu(int num, int page, String content, String methon){
        int size = num;
        int start = (page-1)*num;

        return adminservice.search_stu(start,size,content,methon);
    }

    @GetMapping("/count")
    public int count(){
        return adminservice.count();
    }

    @PostMapping("/del/{id}")
    public int del(@PathVariable String id){
        return adminservice.del(id);
    }

    @PostMapping("/change")
    public int change(String id,String name,String pro,String pwd){
        return adminservice.change(id,name,pro,pwd);
    }

    @PostMapping("/change_pwd")
    public int change_pwd(@RequestBody Admin admin){
       return adminservice.change_pwd(admin);
    }
    @PostMapping("/add")
    public int add(String id,String name,String pro,String pwd){
        return adminservice.add(id,name,pro,pwd);
    }

    //手机验证码登录
    @GetMapping("/phone_login")
    public String phone_login(String phone,String code){

        String c_code = (String) redisTemplate.opsForValue().get(phone);
        System.out.println(c_code);

        if(Objects.equals(c_code, code)){
            return "success";
        }
        else{
            return "error";
        }

    }


    //获取验证码
    @GetMapping("/get_core")
    public String get_core(String phone){

        //判断数据库是否有该电话的用户
        //select * from admin where phone = #{phone}


        //如果有则发送验证码
        if(adminservice.is_phone(phone).size() == 1){

            //如果已经有验证码了,删除再重新生成
            if(redisTemplate.hasKey(phone)){
                redisTemplate.delete(phone);
            }
            //随机生成4位验证码
            String code = String.valueOf((int)((Math.random()*9+1)*1000));

            //将验证码放入Redis
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);



            try {
                //发送短信
                TextConfig textConfig = new TextConfig(phone,code);
                return "success";


            } catch (Exception e) {
                e.printStackTrace();
                //发送短信失败
                return "error";
            }

        }

        else{
            //数据库没有手机号
            return "error";
        }





    }

    @GetMapping("/new_search")
    public Admin new_search(String id){
        return adminMapper.selectById(id);
    }


    @GetMapping("/test")
    public JSONObject test(){
        JSONObject res = new JSONObject();
        res.put("code",0);
        res.put("msg","只因你太美");
        session.setAttribute("kun","ji");
        return res;
    }


    @GetMapping("/test2")
    public void test2(){
        System.out.println(session.getAttribute("kun"));
    }














}
