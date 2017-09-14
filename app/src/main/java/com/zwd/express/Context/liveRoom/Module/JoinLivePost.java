package com.zwd.express.Context.liveRoom.Module;

/**
 * Created by asus-pc on 2017/7/24.
 */

public class JoinLivePost {
    private int userid;
    private int playingid;

    public JoinLivePost(int userid, int playingid) {
        this.userid = userid;
        this.playingid = playingid;
    }

    public int getUserid() {
        return userid;
    }

    public int getPlayingid() {
        return playingid;
    }
}
