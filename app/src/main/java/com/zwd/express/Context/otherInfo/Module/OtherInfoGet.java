package com.zwd.express.Context.otherInfo.Module;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus-pc on 2017/8/11.
 */

public class OtherInfoGet {
    @SerializedName("username")
    private String username;//手机号
    @SerializedName("name")
    private String name;//用户名
    @SerializedName("sex")
    private String sex;
    @SerializedName("credit")
    private int credit;
    @SerializedName("heading")
    private String heading;
    @SerializedName("userstatus")
    private int userstatus;

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public int getCredit() {
        return credit;
    }

    public String getHeading() {
        return heading;
    }

    public int getUserstatus() {
        return userstatus;
    }
}
