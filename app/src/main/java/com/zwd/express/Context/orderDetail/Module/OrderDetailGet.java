package com.zwd.express.Context.orderDetail.Module;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus-pc on 2017/8/5.
 */

public class OrderDetailGet {
    @SerializedName("goodsaddressN")
    private double goodsaddressN;//物品经度
    @SerializedName("goodsaddressE")
    private double goodsaddressE;//物品维度
    @SerializedName("errandaddressN")
    private double errandaddressN;//送达经度
    @SerializedName("errandaddressE")
    private double errandaddressE;//送达维度
    @SerializedName("goodsaddressword")
    private String goodsaddressword;
    @SerializedName("addressword")
    private String addressword;
    @SerializedName("type")
    private int type;//0拿东西1买东西
    @SerializedName("description")
    private String description;
    @SerializedName("price")
    private double price;
    @SerializedName("reward")
    private double reward;
    @SerializedName("userid")
    private String userid;//下单者ID
    @SerializedName("Acceptname")
    private String Acceptname;///接单人名字或下单人名字
    @SerializedName("Acceptphone")
    private String Acceptphone;//接单人电话或下单人电话
    @SerializedName("Acceptuserid")
    private String Acceptuserid;//接单人id或下单人id
    @SerializedName("Acceptuserheading")
    private String Acceptuserheading;//接单人头像或下单人头像
    @SerializedName("starttime")
    private String starttime;
    @SerializedName("endtime")
    private String endtime;

    public double getGoodsaddressN() {
        return goodsaddressN;
    }

    public double getGoodsaddressE() {
        return goodsaddressE;
    }

    public String getStarttime() {
        if (starttime!=null&&starttime.length()>0){
            return starttime.replace("T"," ");
        }else {
            return null;
        }

    }

    public String getEndtime() {
        if(endtime!=null&&endtime.length()>0){
            return endtime.replace("T"," ");
        }else {
            return null;
        }
    }

    public double getErrandaddressN() {
        return errandaddressN;
    }

    public double getErrandaddressE() {
        return errandaddressE;
    }

    public String getAcceptuserheading() {
        return Acceptuserheading;
    }

    public String getUserid() {
        return userid;
    }

    public String getAcceptuserid() {
        return Acceptuserid;
    }

    public String getGoodsaddressword() {
        return goodsaddressword;
    }

    public String getAddressword() {
        return addressword;
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

    public String getAcceptname() {
        return Acceptname;
    }

    public String getAcceptphone() {
        return Acceptphone;
    }
}
