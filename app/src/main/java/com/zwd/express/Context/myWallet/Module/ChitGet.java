package com.zwd.express.Context.myWallet.Module;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus-pc on 2017/7/6.
 */

public class ChitGet {
    @SerializedName("id")
    private int id;
    @SerializedName("starttime")
    private String starttime;
    @SerializedName("endtime")
    private String endtime;
    @SerializedName("mark")
    private int mark;///0/1000积分 10元  1/500积分 5元  2/200积分 2元

    public ChitGet(int id, String starttime, String endtime, int mark) {
        this.id = id;
        this.starttime = starttime;
        this.endtime = endtime;
        this.mark = mark;
    }

    public int getId() {
        return id;
    }

    public String getStarttime() {
        return starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public int getMark() {
        return mark;
    }
}
