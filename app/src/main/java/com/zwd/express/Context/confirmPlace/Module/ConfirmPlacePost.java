package com.zwd.express.Context.confirmPlace.Module;

/**
 * Created by asus-pc on 2017/8/10.
 */

public class ConfirmPlacePost {
    private int stu;
    private int id;
    private int credit;

    public ConfirmPlacePost(int stu, int id, int credit) {
        this.stu = stu;
        this.id = id;
        this.credit = credit;
    }

    public int getStu() {
        return stu;
    }

    public int getId() {
        return id;
    }

    public int getCredit() {
        return credit;
    }
}
