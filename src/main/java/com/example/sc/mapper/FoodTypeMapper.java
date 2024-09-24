package com.example.sc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.sc.entity.FoodType;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FoodTypeMapper extends BaseMapper<FoodType> {

    @Select("select * from food_type")
    @Results({
            @Result(column = "id",property = "id"),
            @Result(column = "type",property = "type"),
            //column 表示传入的参数 property表示实体类的名字
            @Result(column = "type",property = "food",javaType = List.class,many=@Many(select = "com.example.sc.mapper.FoodMapper.find_list"))
    })


    List<FoodType> get_all();


}
