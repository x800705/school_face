package com.example.sc.service;

import com.example.sc.entity.Goods;
import com.example.sc.mapper.PayMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayService {

    @Autowired
    PayMapper payMapper;

    public Goods find(String id){return payMapper.find(id);}

    public int add(Goods goods) {
        return payMapper.add(goods);
    }
}
