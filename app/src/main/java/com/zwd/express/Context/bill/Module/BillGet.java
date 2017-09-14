package com.zwd.express.Context.bill.Module;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus-pc on 2017/7/6.
 */

public class BillGet {
    @SerializedName("dtime")
    private String dtime;
    @SerializedName("pay")
    private String pay;
    @SerializedName("goodseddress")
    private String goodseddress;
    @SerializedName("seteddress")
    private String seteddress;


    public BillGet(String dtime, String pay, String goodseddress, String
            seteddress) {
        this.dtime = dtime;
        this.pay = pay;
        this.goodseddress = goodseddress;
        this.seteddress = seteddress;
    }

    public String getDtime() {
        return dtime;
    }

    public String getPay() {
        return pay;
    }

    public String getGoodseddress() {
        return goodseddress;
    }

    public String getSeteddress() {
        return seteddress;
    }
}
