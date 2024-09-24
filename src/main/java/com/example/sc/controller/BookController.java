package com.example.sc.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.sc.entity.Books;
import com.example.sc.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/book")
@CrossOrigin
public class BookController {

    @Autowired
    BookMapper bookMapper;

    //书本首页查询
    @GetMapping("/menu")
    public Page<Books> find(Books book){
        Page<Books> pages = Page.of(book.getPage(),book.getSize());
        LambdaQueryWrapper<Books> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.likeRight(book.getId()!=null,Books::getId,book.getId());
        lambdaQueryWrapper.likeRight(book.getName()!=null,Books::getName,book.getName());
        lambdaQueryWrapper.likeRight(book.getType()!=null,Books::getType,book.getType());

        return bookMapper.selectPage(pages,lambdaQueryWrapper);

    }

    //书本新增
    @PostMapping("/add")
    public void add(Books book){
        bookMapper.insert(book);
    }

    //书本更改
    @PostMapping("/update")
    public void update(Books book){
        bookMapper.updateById(book);
    }






}
