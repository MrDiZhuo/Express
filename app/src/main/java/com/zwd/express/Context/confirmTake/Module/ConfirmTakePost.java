package com.zwd.express.Context.confirmTake.Module;

/**
 * Created by asus-pc on 2017/8/10.
 */

public class ConfirmTakePost {
    private int stu;
    private int id;
    private double realprice;
    private String picurl;

    public ConfirmTakePost(int stu, int id, double realprice, String picurl) {
        this.stu = stu;
        this.id = id;
        this.realprice = realprice;
        this.picurl = picurl;
    }

    public int getStu() {
        return stu;
    }

    public int getId() {
        return id;
    }

    public double getRealprice() {
        return realprice;
    }

    public String getPicurl() {
        return picurl;
    }
}
