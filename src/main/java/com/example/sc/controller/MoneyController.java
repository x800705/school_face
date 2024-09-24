package com.example.sc.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.sc.entity.sm;
import com.example.sc.mapper.MoneyMapper;
import com.example.sc.service.MoneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/money")
public class MoneyController {
    @Autowired
    private MoneyService moneyService;

    @Autowired
    private MoneyMapper moneyMapper;

    /**
     * 查询单条
     *
     * @param id
     * @return
     */

    @GetMapping("/find/{id}")
    public sm find(@PathVariable String id) {

        return moneyService.find(id);
    }


    @GetMapping("/is_ban/{id}")
    public int ban(@PathVariable String id){
        return moneyService.is_ban(id);
    }



    @GetMapping("/count_stu")
    public int count_stu() {
        return moneyService.count_stu();
    }

    @GetMapping("/count_tea")
    public int count_tea() {
        return moneyService.count_tea();
    }


    @GetMapping("/search_stu")
    public List<sm> search_stu(int num, int page, String content, String methon) {
        int size = num;
        int start = (page - 1) * num;

        return moneyService.search_student(start, size, content, methon);
    }

    @GetMapping("/search_tea")
    public List<sm> search_tea(int num, int page, String content, String methon) {
        int size = num;
        int start = (page - 1) * num;

        return moneyService.search_tea(start, size, content, methon);
    }

    @GetMapping("/new_search")
    public List<sm> new_search(int num, int page, String content, String methon) {
        int size = num;
        int start = (page - 1) * num;
        return moneyService.new_search(start, size, content, methon);
    }

    @PostMapping("/add_stu")
    public int add_money(String id, double money, String date, String content) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dates = null;
        try {
            dates = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return moneyService.add_money(id, money, dates, content);

    }

    @PostMapping("/change_stu")
    public int change_money(String id, double money, String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dates = null;
        try {
            dates = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return moneyService.change_money(id, money, dates);
    }

    @PostMapping("/cost_stu")
    public int cost_money(String id, double money, String date, String content) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date dates = null;
        try {
            dates = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return moneyService.cost_money(id, money, dates, content);

    }

    @PostMapping("/ban")
    public int ban(String id, int is_ban) {
        return moneyService.ban(id, is_ban);
    }

    @GetMapping("/count_list")
    public int list(String id) {
        return moneyService.count_list(id);
    }

    @GetMapping("/list")
    public List<sm> list(String id, int num, int page) {
        int size = num;
        int start = (page - 1) * num;

        return moneyService.list(id, start, size);
    }

    @PostMapping("/pay")
    public int pay(String id, Double money) {
        return moneyService.pay(id, money);
    }

    @GetMapping("/lost")
    public List<sm> lost(int num, int page) {
        int size = num;
        int start = (page - 1) * num;

        return moneyService.lost(start, size);
    }

    @GetMapping("/lost_count")
    public int lost_count() {
        return moneyService.lost_count();
    }


    //分页查询
    @GetMapping("/find_page")
    public Page<sm> find_page(sm money, int page, int size) {
        Page<sm> pages = Page.of(page, size);

        LambdaQueryWrapper<sm> lqw = new LambdaQueryWrapper<>();


        lqw.eq(money.getId() != null, sm::getId, money.getId());

        return moneyMapper.selectPage(pages, lqw);

    }

    //新建账户
    @PostMapping("add_bank/{id}")
    public int add_bank(@PathVariable String id){
        return moneyService.add_bank(id);
    }
}





