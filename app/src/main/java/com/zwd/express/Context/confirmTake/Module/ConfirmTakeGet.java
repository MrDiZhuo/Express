package com.zwd.express.Context.confirmTake.Module;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus-pc on 2017/8/10.
 */

public class ConfirmTakeGet {
    @SerializedName("price")
    private double price;//物品价格
    @SerializedName("reward")
    private double reward;//打赏
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


}
