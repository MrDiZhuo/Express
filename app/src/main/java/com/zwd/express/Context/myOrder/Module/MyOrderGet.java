package com.zwd.express.Context.myOrder.Module;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus-pc on 2017/7/2.
 */

public class MyOrderGet  {
    @SerializedName("id")
    private int id;
    @SerializedName("goodsaddressword")
    private String from;
    @SerializedName("addressword")
    private String to;
    @SerializedName("dtime")
    private String time;
    @SerializedName("realpay")
    private String money;
    @SerializedName("status")
    private int state;
                     //1未完成  2已完成  3已取消 0未接单  4全部
    @SerializedName("sign")
    private int type;//0下单  1接单
    @SerializedName("stu")
    private int stu;//0跑腿单1直播单

    public int getId() {
        return id;
    }

    public int getStu() {
        return stu;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTime() {
        return time.replace("T"," ");
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
