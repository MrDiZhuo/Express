package com.zwd.express.Context.identity.Module;

/**
 * Created by asus-pc on 2017/8/9.
 */

public class IndentityPost {
    private int userid;
    private String name;
    private String number;
    private String picup;
    private String picdown;

    public IndentityPost(int userid, String name, String number, String
            picup, String picdown) {
        this.userid = userid;
        this.name = name;
        this.number = number;
        this.picup = picup;
        this.picdown = picdown;
    }

    public int getUserid() {
        return userid;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getPicup() {
        return picup;
    }

    public String getPicdown() {
        return picdown;
    }
}
