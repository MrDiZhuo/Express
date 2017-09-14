package com.zwd.express.Context.roastDetail.Module;

/**
 * Created by asus-pc on 2017/7/19.
 */

public class ReplyPost {
    private int chatid;//吐槽id   评论id
    private int userid;//评论者id  回复者id
    private String comment;
    private String dtime;
    private int targetid;//回复对象id

    public ReplyPost(int chatid, int userid, String comment, String dtime) {
        this.chatid = chatid;
        this.userid = userid;
        this.comment = comment;
        this.dtime = dtime;
    }

    public ReplyPost(int chatid, int userid, String comment, String dtime,
                     int targetid) {
        this.chatid = chatid;
        this.userid = userid;
        this.comment = comment;
        this.dtime = dtime;
        this.targetid = targetid;
    }

    public int getTargetid() {
        return targetid;
    }

    public int getChatid() {
        return chatid;
    }

    public void setChatid(int chatid) {
        this.chatid = chatid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDtime() {
        return dtime;
    }

    public void setDtime(String dtime) {
        this.dtime = dtime;
    }
}
