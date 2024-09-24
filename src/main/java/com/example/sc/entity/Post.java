package com.example.sc.entity;

import java.util.Date;
import java.util.List;

public class Post {
    public int id;
    public int log_id;
    public String post_id;
    public String content;
    public Date time;
    public int count;


    public List<Student> students;


    public int getCount() {
        return count;
    }

    public Date getTime() {
        return time;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLog_id(int log_id) {
        this.log_id = log_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Student> getStudents() {
        return students;
    }

    public int getId() {
        return id;
    }

    public String getPost_id() {
        return post_id;
    }

    public String getContent() {
        return content;
    }

    public int getLog_id() {
        return log_id;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", log_id=" + log_id +
                ", post_id='" + post_id + '\'' +
                ", content='" + content + '\'' +
                ", time=" + time +
                ", count=" + count +
                ", students=" + students +
                '}';
    }
}
