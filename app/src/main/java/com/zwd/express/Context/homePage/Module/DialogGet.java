package com.zwd.express.Context.homePage.Module;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus-pc on 2017/7/27.
 */

public class DialogGet {
    @SerializedName("id")
    private int id;
    @SerializedName("type")
    private int type;//0拿东西，1买东西
    @SerializedName("goodsaddressword")
    private String goodsaddressword;//物品位置
    @SerializedName("addressword")
    private String addressword;//送达位置
    @SerializedName("starttime")
    private String starttime;//起始时间
    @SerializedName("endtime")
    private String endtime;//结束时间
    @SerializedName("reward")
    private double reward;//打赏
    @SerializedName("userid")
    private int userid;

    public DialogGet(int id, int type, String goodsaddressword, String
            addressword, String starttime, String endtime, double reward,int userid) {
        this.id = id;
        this.type = type;
        this.goodsaddressword = goodsaddressword;
        this.addressword = addressword;
        this.starttime = starttime;
        this.endtime = endtime;
        this.reward = reward;
        this.userid = userid;
    }

    public int getUserid() {
        return userid;
    }

    public int getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public String getGoodsaddressword() {
        return goodsaddressword;
    }

    public String getAddressword() {
        return addressword;
    }

    public String getStarttime() {
        if (starttime.length()>0){
            return starttime.replace("T"," ");
        }else return null;

    }

    public String getEndtime() {
        if (endtime.length()>0){
            return endtime.replace("T"," ");
        }else return null;
    }

    public double getReward() {
        return reward;
    }
}
