package com.example.sc.entity;

public class User {


    private String name;
    private String pwd;
    private int ban;

    public void setBan(int ban) {
        this.ban = ban;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public int getBan() {
        return ban;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                ", ban=" + ban +
                '}';
    }
}
