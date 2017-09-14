package com.zwd.express.Context.confirmPlace.Module;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus-pc on 2017/8/10.
 */

public class ConfirmPlaceGet {
    @SerializedName("price")
    private double price;//物品价格
    @SerializedName("reward")
    private double reward;//打赏
    @SerializedName("realprice")
    private double realprice;////接单人填写的实际消费
    @SerializedName("picurl")
    private String picurl;////小票
    @SerializedName("name")
    private String name;//接单人姓名
    @SerializedName("usernane")
    private String usernane;///号码
    @SerializedName("heading")
    private String heading;//头像

    public String getName() {
        return name;
    }

    public String getUsernane() {
        return usernane;
    }

    public String getHeading() {
        return heading;
    }

    public double getPrice() {
        return price;
    }

    public double getReward() {
        return reward;
    }

    public double getRealprice() {
        return realprice;
    }

    public String getPicurl() {
        return picurl;
    }
}
