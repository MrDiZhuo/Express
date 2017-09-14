package com.zwd.express.Context.register.Module;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus-pc on 2017/8/10.
 */

public class RegisterGet {
    @SerializedName("id")
    private int id;
    @SerializedName("qiniutoken")
    private String qiniutoken;

    public int getId() {
        return id;
    }

    public String getQiniutoken() {
        return qiniutoken;
    }
}
