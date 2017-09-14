package com.zwd.express.Context.register.Module;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus-pc on 2017/5/9.
 */

public class VertifyGet {

    @SerializedName("randkey")
    private String randkey;

    public String getRandkey() {
        return randkey;
    }
}
