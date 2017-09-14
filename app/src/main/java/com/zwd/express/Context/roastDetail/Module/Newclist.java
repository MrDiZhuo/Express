package com.zwd.express.Context.roastDetail.Module;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus-pc on 2017/7/19.
 */

public class Newclist {
    @SerializedName("userid")
    private int userid;
    @SerializedName("userpicing")
    private String userpicing;
    @SerializedName("username")
    private String username;
    @SerializedName("targetname")
    private String targetname;
    @SerializedName("comment")
    private String comment;
    @SerializedName("dtime")
    private String dtime;

    public Newclist(int userid, String userpicing, String username, String
            targetname, String comment, String dtime) {
        this.userid = userid;
        this.userpicing = userpicing;
        this.username = username;
        this.targetname = targetname;
        this.comment = comment;
        this.dtime = dtime;
    }

    public int getUserid() {
        return userid;
    }

    public String getUserpicing() {
        return userpicing;
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

    public String getDtime() {
        if (dtime!=null&&dtime.length()>0){
            return dtime.replace("T"," ");
        }
        else return dtime;
    }
}
