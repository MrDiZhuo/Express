package com.zwd.express.Context.roast.Module;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus-pc on 2017/7/13.
 */

public class ReplyGet {
    private int flag_look=0;///0没看  1看了
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;//用户名
    @SerializedName("type")
    private int type;//0评论  1回复
    @SerializedName("chat")
    private String object;//评论对象的内容
    @SerializedName("comment")
    private String context;///评论内容
    @SerializedName("tragetid")
    private int tragetid;


    public ReplyGet(int flag_look, int id, String name, int type, String
            object, String context, int tragetid) {
        this.flag_look = flag_look;
        this.id = id;
        this.name = name;
        this.type = type;
        this.object = object;
        this.context = context;
        this.tragetid = tragetid;
    }

    public int getTragetid() {
        return tragetid;
    }

    public int getId() {
        return id;
    }

    public int getFlag_look() {
        return flag_look;
    }

    public void setFlag_look(int flag_look) {
        this.flag_look = flag_look;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

}
