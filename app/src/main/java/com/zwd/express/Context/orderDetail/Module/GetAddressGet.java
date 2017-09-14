package com.zwd.express.Context.orderDetail.Module;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus-pc on 2017/8/9.
 */

public class GetAddressGet {
    @SerializedName("addressN")
    private double addressN;
    @SerializedName("addressE")
    private double addressE;

    public double getAddressN() {
        return addressN;
    }

    public double getAddressE() {
        return addressE;
    }
}
