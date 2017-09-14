package com.zwd.express.Context.roast.Module;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by asus-pc on 2017/7/9.
 */

public class RoastListGet {
    @SerializedName("id")
    private int id;//说说ID
    @SerializedName("userid")
    private int userid;//说说主人id
    @SerializedName("name")
    private String name;//吐槽人名字
    @SerializedName("picing")
    private String picing;//吐槽人头像
    @SerializedName("chatcontent")
    private String chatcontent;
    @SerializedName("chattime")
    private String chattime;
    @SerializedName("approve")
    private int approve;//赞数
    @SerializedName("com")
    private int com;//评论
    @SerializedName("yon")
    private int yon;//0用户未点赞  1已点
    @SerializedName("plist")
    private List<PicingGet> plist;//图片

    public RoastListGet(int id, int userid, String name, String picing,
                        String chatcontent, String chattime, int approve, int
                                com, int yon, List<PicingGet> plist) {
        this.id = id;
        this.userid = userid;
        this.name = name;
        this.picing = picing;
        this.chatcontent = chatcontent;
        this.chattime = chattime;
        this.approve = approve;
        this.com = com;
        this.yon = yon;
        this.plist = plist;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicing() {
        return picing;
    }

    public void setPicing(String picing) {
        this.picing = picing;
    }

    public String getChatcontent() {
        return chatcontent;
    }

    public void setChatcontent(String chatcontent) {
        this.chatcontent = chatcontent;
    }

    public String getChattime() {
        return chattime.replace("T"," ");
    }

    public void setChattime(String chattime) {
        this.chattime = chattime;
    }

    public int getApprove() {
        return approve;
    }

    public void setApprove(int approve) {
        this.approve = approve;
    }

    public int getCom() {
        return com;
    }

    public void setCom(int com) {
        this.com = com;
    }

    public int getYon() {
        return yon;
    }

    public void setYon(int yon) {
        this.yon = yon;
    }

    public List<PicingGet> getPlist() {
        return plist;
    }

    public void setPlist(List<PicingGet> plist) {
        this.plist = plist;
    }


}
