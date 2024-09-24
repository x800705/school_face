package com.example.sc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.sc.entity.StudentMoney;
import com.example.sc.entity.sm;
import org.apache.ibatis.annotations.*;


import java.util.List;

@Mapper
public interface SmMapper extends BaseMapper<sm> {
    @Select("select * from sm")
    @Results({

            @Result(column = "id",property = "id"),
            @Result(column = "money",property = "money"),
            @Result(column = "ban",property = "ban"),
            //column 表示传入的参数 property表示实体类的名字
            @Result(column = "id",property = "student",one=@One(select = "com.example.sc.mapper.StudentMapper.get_student"))
    })
    List<sm> find();


    @Select("select * from student_money")
    StudentMoney find(String id);




}
