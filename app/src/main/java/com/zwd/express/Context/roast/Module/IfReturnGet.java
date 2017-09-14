package com.zwd.express.Context.roast.Module;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus-pc on 2017/7/26.
 */

public class IfReturnGet {
    @SerializedName("tatus")
    private int tatus;//0表示没有 1表示有

    public IfReturnGet(int tatus) {
        this.tatus = tatus;
    }

    public int getTatus() {
        return tatus;
    }
}
