package com.example.sc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.sc.entity.StuBook;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

@Mapper
public interface StudentBookMapper extends BaseMapper<StuBook> {

    @Select("select * from stu_book where id = #{id}")
    StuBook find(String id);

    @Select("SELECT TYPE,COUNT(*) FROM book_bill WHERE id=#{id} GROUP BY TYPE")
    String count(String id);




}
