package com.zwd.express.Context.myWallet.Module;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus-pc on 2017/7/26.
 */

public class MyWalletGet {
    @SerializedName("coupon")
    private int coupon;//代金券数量
    @SerializedName("surplus")
    private double surplus;//余额

    public MyWalletGet(int coupon, double surplus) {
        this.coupon = coupon;
        this.surplus = surplus;
    }

    public int getCoupon() {
        return coupon;
    }

    public double getSurplus() {
        return surplus;
    }
}
