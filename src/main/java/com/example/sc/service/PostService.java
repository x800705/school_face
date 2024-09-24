package com.example.sc.service;

import com.example.sc.entity.Post;
import com.example.sc.mapper.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class PostService {

    @Autowired
    PostMapper postMapper;


    public void post(String post_id,String content,String time){postMapper.post(post_id,content,time);}

    public void post_log(int log_id, String content, String post_id, String time){

        postMapper.post_log(log_id,content,post_id,time);
        postMapper.count(log_id);

    }

    public List<Post> find(){return postMapper.find();}

    public List<Post> find_one(int id){return postMapper.find_one(id);}

    public List<Post> get_log(int log_id){return postMapper.get_log(log_id);}


    public void up_time(int log_id, String time) {
        postMapper.up_time(log_id,time);
    }

    public List<Post> like_find(String title) {
        return postMapper.like_find(title);
    }


    public List<Post> finds() {
        return postMapper.finds();
    }

    public List<Post> get_logs(int log_id) {
        return postMapper.get_logs(log_id);
    }

    public int count(int id) {
        return postMapper.counts(id);
    }
}
