package com.example.sc.controller;


import com.example.sc.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.sc.entity.Post;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin

@RequestMapping("/post")
public class PostController {


    @Autowired
    PostService postService;


    @PostMapping("/new")
    public void post(String post_id,String title,String time){
        postService.post(post_id,title,time);
    }

    @GetMapping("/find")
    public List<Post> find(){
        return postService.find();
    }

    @GetMapping("/finds")
    public List<Post> finds(){
        return postService.finds();
    }


    @GetMapping("/like_find")
    public List<Post> like_find(String title){
        return postService.like_find(title);
    }

    @GetMapping("/findOne")
    public List<Post> find(int id){
        return postService.find_one(id);
    }

    @PostMapping("/newlog")
    public void post_log(int log_id, String content, String post_id, String time){
        postService.post_log(log_id,content,post_id,time);
        postService.up_time(log_id,time);
    }

    @PostMapping("/getlog")
    public List<Post> get_log(int log_id){
        return postService.get_log(log_id);
    }

    @PostMapping("/getlogs")
    public List<Post> get_logs(int log_id){
        return postService.get_logs(log_id);
    }

    @GetMapping("/count")
    public int count(int id) {return postService.count(id);}







}
