package com.example.sc.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.sc.entity.Goods;
import com.example.sc.service.PayService;
import com.example.sc.service.WebSocket;
import com.fasterxml.jackson.annotation.JsonAlias;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping("/pay")
public class PayController {

    @Autowired
    PayService payService;

    @GetMapping("/find/{id}")
    public Goods find(@PathVariable String id){
        return payService.find(id);
    }

    @PostMapping("/pay")
    public void pay(String id,Double money){
        //发送WebSocket请求
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",id);
        jsonObject.put("money",money);
        jsonObject.put("msg","支付请求");
        WebSocket.sendMessage(String.valueOf(jsonObject));

    }

    @PostMapping("/pay_over")
    public void pay_over(String id){
        //发送WebSocket请求
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",id);
        jsonObject.put("msg","支付成功");
        WebSocket.sendMessage(String.valueOf(jsonObject));

    }


    //添加菜品
    @PostMapping("/add")
    public int add(Goods goods){
        return payService.add(goods);

    }





}
