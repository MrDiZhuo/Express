package com.zwd.express.Context.Locationservice.Module;

/**
 * Created by asus-pc on 2017/8/7.
 */

public class MyServicePost {
    private int id;
    private double addressN;
    private double addressE;
    private String addressword;

    public MyServicePost(int id, double addressN, double addressE, String
            addressword) {
        this.id = id;
        this.addressN = addressN;
        this.addressE = addressE;
        this.addressword = addressword;
    }

    public int getId() {
        return id;
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
}
