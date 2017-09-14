package com.zwd.express.Context.liveTake.Module;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus-pc on 2017/7/21.
 */

public class LiveTakeGet {
    @SerializedName("id")
    private String id;
    @SerializedName("userid")
    private int userid;
    @SerializedName("address")
    private String addr;
    @SerializedName("reward")
    private String reward;
    @SerializedName("acceptmoney")
    private String money;

    public String getId() {
        return id;
    }

    public int getUserid() {
        return userid;
    }

    public String getAddr() {
        return addr;
    }

    public String getReward() {
        return reward;
    }

    public String getMoney() {
        return money;
    }
}
