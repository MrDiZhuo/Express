package com.zwd.express.Context.login.Module;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus-pc on 2017/7/16.
 */

public class LoginGet {
    @SerializedName("id")
    private int id;/////融云id
    @SerializedName("name")
    private String name;
    @SerializedName("heading")
    private String heading;
    @SerializedName("token")
    private String token;
    @SerializedName("username")
    private String username;
    @SerializedName("sex")
    private String sex;
    @SerializedName("credit")
    private int credit;///信用度
    @SerializedName("mark")
    private int mark;//积分
    @SerializedName("userstatus")
    private int userstatus;///0未认证  1认证
    @SerializedName("qiniutoken")
    private String qiniutoken;

    public LoginGet(int id, String token) {
        this.id = id;
        this.token = token;
    }

    public int getUserstatus() {
        return userstatus;
    }

    public String getQiniutoken() {
        return qiniutoken;
    }

    public int getCredit() {
        return credit;
    }

    public int getMark() {
        return mark;
    }

    public String getSex() {
        return sex;
    }

    public String getUsername() {
        return username;
    }

    public int getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public String getName() {
        return name;
    }

    public String getHeading() {
        return heading;
    }
}
