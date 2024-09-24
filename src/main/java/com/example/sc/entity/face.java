package com.example.sc.entity;

public class face {
    private int id;
    private int is_use;
    private double money;

    public void setId(int id) {
        this.id = id;
    }

    public void setIs_use(int is_use) {
        this.is_use = is_use;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getId() {
        return id;
    }

    public int getIs_use() {
        return is_use;
    }

    public double getMoney() {
        return money;
    }

    @Override
    public String toString() {
        return "face{" +
                "id=" + id +
                ", is_use=" + is_use +
                ", money=" + money +
                '}';
    }
}
