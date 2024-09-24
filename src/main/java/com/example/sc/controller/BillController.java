package com.example.sc.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.sc.entity.Bill;
import com.example.sc.entity.CheckIn;
import com.example.sc.mapper.BillMapper;
import com.example.sc.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("bill")
public class BillController {

    @Autowired
    BillService billService;


    @Autowired
    BillMapper billMapper;


    @GetMapping("/search")
    public Page<Bill> search(Bill bill, int page, int size){
        Page<Bill> pages = Page.of(page,size);

        LambdaQueryWrapper<Bill> lqw = new LambdaQueryWrapper<>();





        lqw.eq(bill.getId() != null,Bill::getId,bill.getId());
        lqw.eq(bill.getStu_name() != null,Bill::getStu_name,bill.getStu_name());
        lqw.eq(bill.getState() != null,Bill::getState,bill.getState());
        lqw.orderByDesc(Bill::getId);
        return billMapper.selectPage(pages,lqw);




    }

    @PostMapping("del/{id}")
    public void del(@PathVariable String id){
        System.out.println(id);
        billMapper.deleteById(id);
    }

    @GetMapping("/find")
    public Page<Bill> find(String id,int page){
        Page<Bill> pages = Page.of(page,10);

        LambdaQueryWrapper<Bill> lqw = new LambdaQueryWrapper<>();


        lqw.eq(Bill::getId,id);

        return billMapper.selectPage(pages, lqw);

    }

}
