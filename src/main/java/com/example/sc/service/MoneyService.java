package com.example.sc.service;


import com.example.sc.entity.sm;
import com.example.sc.mapper.MoneyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author zls
 */
@Service
public class MoneyService {

    @Autowired
    private MoneyMapper moneyMapper;


    /**
     * 查询单条
     *
     * @param id
     * @return
     */
    public sm find(String id) {
        return moneyMapper.find(id);
    }




    public List<sm> search_stu(int start, int size, String content, String methon) {
        if(methon.equals("id")){
            return moneyMapper.search_by_id(start,size,content);
        }
        else if(methon.equals("name")){
            return moneyMapper.search_by_name(start,size,content);
        }
        else{
            return moneyMapper.search_by_class(start,size,content);
        }
    }

    public List<sm> lost(int start,int size){
        return moneyMapper.lost(start,size);
    }



    public int count_stu() {
        return moneyMapper.count_stu();
    }

    public int add_money(String id, double money, Date date, String content) {
        if(moneyMapper.add_money(id,money) == 1) return moneyMapper.add_consume(id,money,date,content);
        else return 0;
    }

    public int change_money(String id,double money,Date date){
        if(moneyMapper.change_money(id,money) == 1) return moneyMapper.add_consume(id,0,date,"人为更改");
        else return 0;
    }


    public int ban(String id,int is_ban) {
        if(is_ban == 1){
            return moneyMapper.ban(id);
        }
        else{
            return moneyMapper.no_ban(id);
        }
    }


    public int count_list(String id) {
        return moneyMapper.count_list(id);
    }

    public List<sm> list(String id, int start, int size) {
        return moneyMapper.list(id,start,size);
    }

    public int pay(String id, Double money) {
        return moneyMapper.pay(id,money);
    }

    public int lost_count() {
        return moneyMapper.count_lost();
    }

    public List<sm> new_search(int start, int size, String content, String methon) {
        return moneyMapper.search(start,size,content,methon);
    }

    public int count_tea() {
        return moneyMapper.count_tea();
    }

    public List<sm> search_tea(int start, int size, String content, String methon) {
        return moneyMapper.search_tea(start,size,content,methon);
    }

    public List<sm> search_student(int start, int size, String content, String methon) {
        return moneyMapper.search_stu(start,size,content,methon);
    }

    public int is_ban(String id) {
        return moneyMapper.is_ban(id);
    }

    public int cost_money(String id, double money, Date dates, String content) {
        return moneyMapper.cost_consume(id,money,dates,content);
    }

    public int add_bank(String id) {
        return moneyMapper.add_bank(id);
    }
}
