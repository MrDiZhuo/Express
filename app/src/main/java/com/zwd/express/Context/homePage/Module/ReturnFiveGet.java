package com.zwd.express.Context.homePage.Module;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus-pc on 2017/7/24.
 */

public class ReturnFiveGet {
    @SerializedName("playingname")
    private String playingname;//房间名
    @SerializedName("introduce")
    private String introduce;//房间介绍
    @SerializedName("picurl")
    private String picurl;///直播图片地址
    @SerializedName("cou")
    private int cou;///观看人数
    @SerializedName("pay")
    private String pay;
    @SerializedName("id")
    private int id;///直播房间id
    @SerializedName("tokenurl")
    private String tokenurl;///拉流地址
    @SerializedName("rongid")
    private String rongid;///融云房间地址
    @SerializedName("userid")
    private String userid;///创建用户id

    private int TYPE;////1  2


    public int getTYPE() {
        return TYPE;
    }

    public void setTYPE(int TYPE) {
        this.TYPE = TYPE;
    }

    public String getPlayingname() {
        return playingname;
    }

    public String getIntroduce() {
        return introduce;
    }

    public String getPicurl() {
        return picurl;
    }

    public int getCou() {
        return cou;
    }

    public String getPay() {
        return pay;
    }

    public int getId() {
        return id;
    }

    public String getTokenurl() {
        return tokenurl;
    }

    public String getRongid() {
        return rongid;
    }

    public String getUserid() {
        return userid;
    }
}
