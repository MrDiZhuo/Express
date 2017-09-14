package com.zwd.express.Context.homePage.Module;

/**
 * Created by asus-pc on 2017/7/17.
 */

public class PlacePost {
    private int id;
    private String  goodsaddressword;//物品位置
    private double goodsaddressN;//经度
    private double goodsaddressE;//纬度
    private String  addressword;//送达位置
    private double errandaddressN;//经度
    private double errandaddressE;//纬度
    private String starttime;//时间区间
    private String endtime;
    private String name;
    private String phone;
    private int type;//0拿东西 1买东西
    private String description;//说明
    private double price;//价格
    private double reward;//打赏
    private int status;//0初始状态 1有人接单 2结束 3失败
    private int couponid;///代金券id
    private double couponpay;///代金券金额
    private double realpay;//实付金额

    public PlacePost(int id, String goodsaddressword, double goodsaddressN,
                     double goodsaddressE, String addressword, double
                             errandaddressN, double errandaddressE, String
                             starttime, String endtime, String name, String
                             phone, int type, String description, double
                             price, double reward, int status,int couponid
            ,double couponpay,double realpay) {
        this.id = id;
        this.goodsaddressword = goodsaddressword;
        this.goodsaddressN = goodsaddressN;
        this.goodsaddressE = goodsaddressE;
        this.addressword = addressword;
        this.errandaddressN = errandaddressN;
        this.errandaddressE = errandaddressE;
        this.starttime = starttime;
        this.endtime = endtime;
        this.name = name;
        this.phone = phone;
        this.type = type;
        this.description = description;
        this.price = price;
        this.reward = reward;
        this.status = status;
        this.couponid = couponid;
        this.couponpay = couponpay;
        this.realpay = realpay;
    }

    public int getCouponid() {
        return couponid;
    }

    public double getCouponpay() {
        return couponpay;
    }

    public double getRealpay() {
        return realpay;
    }

    public int getId() {
        return id;
    }

    public String getGoodsaddressword() {
        return goodsaddressword;
    }

    public double getGoodsaddressN() {
        return goodsaddressN;
    }

    public double getGoodsaddressE() {
        return goodsaddressE;
    }

    public String getAddressword() {
        return addressword;
    }

    public double getErrandaddressN() {
        return errandaddressN;
    }

    public double getErrandaddressE() {
        return errandaddressE;
    }

    public String getStarttime() {
        return starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public int getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public double getReward() {
        return reward;
    }

    public int getStatus() {
        return status;
    }
}
