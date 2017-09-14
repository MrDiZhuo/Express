package com.zwd.express.Context.roastDetail.Module;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by asus-pc on 2017/7/12.
 */

public class CommentGet {
    @SerializedName("userid")
    private int userid;///评论者id
    @SerializedName("picing")
    private String picing;///头像
    @SerializedName("name")
    private String name;
    @SerializedName("comment")
    private String comment;
    @SerializedName("newclist")
    private List<Newclist> newclist;

    public CommentGet(int userid, String picing, String name, String comment,
                      List<Newclist> newclist) {
        this.userid = userid;
        this.picing = picing;
        this.name = name;
        this.comment = comment;
        this.newclist = newclist;
    }

    public int getUserid() {
        return userid;
    }

    public String getPicing() {
        return picing;
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public List<Newclist> getNewclist() {
        return newclist;
    }
}
