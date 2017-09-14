package com.zwd.express.Context.roast.Module;

import java.util.List;

/**
 * Created by asus-pc on 2017/7/18.
 */

public class PublishRoastPost {
    private int userid;
    private String chatcontent;
    private int approve;//初始为0
    private List<String> plist;

    public PublishRoastPost(int userid, String chatcontent,
                            int approve, List<String> plist) {
        this.userid = userid;
        this.chatcontent = chatcontent;
        this.approve = approve;
        this.plist = plist;
    }

    public int getUserid() {
        return userid;
    }

    public String getChatcontent() {
        return chatcontent;
    }

    public int getApprove() {
        return approve;
    }

    public List<String> getPlist() {
        return plist;
    }
}
