package com.example.sc.service;

import com.example.sc.entity.Bill;
import com.example.sc.entity.Food;
import com.example.sc.entity.Goods;
import com.example.sc.mapper.FoodMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class FoodService {
    @Autowired
    FoodMapper foodMapper;

    public List<Goods> menu(String type) {
        if(Objects.equals(type, "menu")) return foodMapper.all();
        else return foodMapper.menu(type);
    }


    public List<Bill> bill() {
        return foodMapper.bill();
    }

    public int add_bill(Double money,String content,String date) {
        return foodMapper.add_bill(money,content,date);
    }

    public void payed(String id,String name) {
        foodMapper.payed(id,name);
    }

    public int add(Food food){return foodMapper.insert(food);}


    public int change(String id, String state,Double money) {
        foodMapper.change(id,state);
        if(Objects.equals(state, "已完成")){
            foodMapper.bill_money(money);
            return 1;
        }
        return 1;
    }

    public int get_id() {
        return foodMapper.get_id();
    }
}
