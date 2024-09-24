package com.example.sc.entity;

import java.util.Date;

public class consume {
    private String id;
    private Double money;
    private Date date;
    private String content;
    private Double last;

    public String getId() {
        return id;
    }

    public Double getMoney() {
        return money;
    }

    public Date getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public Double getLast(){
        return last;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setLast(Double last){
        this.last = last;
    }

    @Override
    public String toString() {
        return "consume{" +
                "id='" + id + '\'' +
                ", money=" + money +
                ", date=" + date +
                ", content='" + content + '\'' +
                ", last=" + last +
                '}';
    }
}
