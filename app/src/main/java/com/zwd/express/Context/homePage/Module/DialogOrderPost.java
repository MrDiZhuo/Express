package com.zwd.express.Context.homePage.Module;

import java.util.List;

/**
 * Created by asus-pc on 2017/7/27.
 */

public class DialogOrderPost {
    private double addressN;//接单经度
    private double addressE;//接单维度
    private String addressword;//接单地址
    private int userid;//接单用户id
    private List<Integer> ids;//接单列表

    public DialogOrderPost(double addressN, double addressE, String
            addressword, int userid, List<Integer> ids) {
        this.addressN = addressN;
        this.addressE = addressE;
        this.addressword = addressword;
        this.userid = userid;
        this.ids = ids;
    }

    public double getAddressN() {
        return addressN;
    }

    public double getAddressE() {
        return addressE;
    }

    public String getAddressword() {
        return addressword;
    }

    public int getUserid() {
        return userid;
    }

    public List<Integer> getIds() {
        return ids;
    }
}
