package com.zwd.express.Context.orderDetail.Module;

/**
 * Created by asus-pc on 2017/8/5.
 */

public class OrderDetailPost {
    private int sign;
    private int stu;
    private int id;

    public OrderDetailPost(int stu, int id) {
        this.stu = stu;
        this.id = id;
    }

    public OrderDetailPost(int id, int stu, int sign) {
        this.sign = sign;
        this.stu = stu;
        this.id = id;
    }

    public int getSign() {
        return sign;
    }

    public int getStu() {
        return stu;
    }

    public int getId() {
        return id;
    }
}
