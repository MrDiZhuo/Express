package com.zwd.express.Context.integrate.Module;

/**
 * Created by asus-pc on 2017/7/26.
 */

public class GetCouponPost {
    private int userid;
    private Double money;
    private int mark;//积分

    public GetCouponPost(int userid, Double money, int mark) {
        this.userid = userid;
        this.money = money;
        this.mark = mark;
    }

    public int getUserid() {
        return userid;
    }

    public Double getMoney() {
        return money;
    }

    public int getMark() {
        return mark;
    }
}
