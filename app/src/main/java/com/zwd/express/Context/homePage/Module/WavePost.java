package com.zwd.express.Context.homePage.Module;

/**
 * Created by asus-pc on 2017/7/27.
 */

public class WavePost {
    private double addressN;//经度
    private double addressE;//纬度
    private String date;//当前时间

    public WavePost(double addressN, double addressE, String date) {
        this.addressN = addressN;
        this.addressE = addressE;
        this.date = date;
    }

    public double getAddressN() {
        return addressN;
    }

    public double getAddressE() {
        return addressE;
    }

    public String getDate() {
        return date;
    }
}
