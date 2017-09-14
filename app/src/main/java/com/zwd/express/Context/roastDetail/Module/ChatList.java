package com.zwd.express.Context.roastDetail.Module;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by asus-pc on 2017/7/19.
 */

public class ChatList {
    @SerializedName("id")
    private int id;
    @SerializedName("userid")
    private int userid;
    @SerializedName("name")
    private String name;
    @SerializedName("picing")
    private String picing;
    @SerializedName("comment")
    private String comment;
    @SerializedName("dtime")
    private String dtime;
    @SerializedName("ChatCR")
    private ChatCR chatCR;

    public ChatList(int id, int userid, String name, String picing, String
            comment, String dtime, ChatCR chatCR) {
        this.id = id;
        this.userid = userid;
        this.name = name;
        this.picing = picing;
        this.comment = comment;
        this.dtime = dtime;
        this.chatCR = chatCR;
    }

    public int getId() {
        return id;
    }

    public int getUserid() {
        return userid;
    }

    public String getName() {
        return name;
    }

    public String getPicing() {
        return picing;
    }

    public String getComment() {
        return comment;
    }

    public String getDtime() {
        if (dtime!=null&&dtime.length()>0){
            return dtime.replace("T"," ");
        }
        else return dtime;
    }

    public ChatCR getChatCR() {
        return chatCR;
    }
}
