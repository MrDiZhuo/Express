package com.zwd.express.Context.roastDetail.Module;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus-pc on 2017/7/19.
 */

public class ChatCR {
    @SerializedName("username")
    private String username;///回复人
    @SerializedName("targetname")
    private String targetname;//回复对象
    @SerializedName("comment")
    private String comment;//内容

    public ChatCR(String username, String targetname, String comment) {
        this.username = username;
        this.targetname = targetname;
        this.comment = comment;
    }

    public String getUsername() {
        return username;
    }

    public String getTargetname() {
        return targetname;
    }

    public String getComment() {
        return comment;
    }
}
