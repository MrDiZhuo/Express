package com.zwd.express.Context.roast.Module;

/**
 * Created by asus-pc on 2017/7/18.
 */

public class ProvePost {
    private int userid;
    private int chatid;

    public ProvePost(int userid, int chatid) {
        this.userid = userid;
        this.chatid = chatid;
    }

    public int getUserid() {
        return userid;
    }

    public int getChatid() {
        return chatid;
    }
}
