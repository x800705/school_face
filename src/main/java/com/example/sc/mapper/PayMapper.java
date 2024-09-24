package com.example.sc.mapper;

import com.example.sc.entity.Goods;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PayMapper {

    @Select("select * from goods where id=#{id}")
    public Goods find(@Param("id") String id);


    @Insert("insert into food_bill values (#{goods.id},#{goods.money},#{goods.state})")
    int add(@Param("goods") Goods goods);


}
