package com.zwd.express.Context.roast.Module;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus-pc on 2017/7/18.
 */

public class PicingGet {
    @SerializedName("picing")
    private String picing;

    public PicingGet(String picing) {
        this.picing = picing;
    }

    public String getPicing() {
        return picing;
    }
}
