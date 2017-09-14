package com.zwd.express.Context.liveRoom.Module;

/**
 * Created by asus-pc on 2017/7/25.
 */

public class LiveOrderPost {
    private int userid;//下单用户id
    private int playingid;//直播间id
    private String address;//送货地址
    private String name;//收货人名字
    private String phone;//收货手机号
    private double acceptmoney;//商品付款
    private double reward;//打赏
    private String introduce;//说明
    private int couponid;//代金券id
    private double couponpay;//代金券金额
    private double realpayDecimal;//实际付款

    public LiveOrderPost(int userid, int playingid, String address, String
            name, String phone, double acceptmoney, double reward, String
            introduce,int couponid,double couponpay,double realpayDecimal) {
        this.couponid = couponid;
        this.couponpay = couponpay;
        this.realpayDecimal = realpayDecimal;
        this.userid = userid;
        this.playingid = playingid;
        this.address = address;
        this.name = name;
        this.phone = phone;
        this.acceptmoney = acceptmoney;
        this.reward = reward;
        this.introduce = introduce;
    }

    public int getCouponid() {
        return couponid;
    }

    public double getCouponpay() {
        return couponpay;
    }

    public double getRealpayDecimal() {
        return realpayDecimal;
    }

    public int getUserid() {
        return userid;
    }

    public int getPlayingid() {
        return playingid;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public double getAcceptmoney() {
        return acceptmoney;
    }

    public double getReward() {
        return reward;
    }

    public String getIntroduce() {
        return introduce;
    }
}
