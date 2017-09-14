package com.zwd.express.Context.identity.Module;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus-pc on 2017/8/9.
 */

public class IndentityGet {
    @SerializedName("name")
    private String name;
    @SerializedName("number")
    private String number;
    @SerializedName("userstatus")
    private int userstatus;///0未认证   1认证

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public int getUserstatus() {
        return userstatus;
    }
}
