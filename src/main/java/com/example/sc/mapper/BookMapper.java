package com.example.sc.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.sc.entity.Books;
import org.apache.ibatis.annotations.Mapper;

import java.awt.print.Book;

@Mapper
public interface BookMapper extends BaseMapper<Books> {

}
