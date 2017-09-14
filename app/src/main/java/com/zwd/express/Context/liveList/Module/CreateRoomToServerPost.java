package com.zwd.express.Context.liveList.Module;

/**
 * Created by asus-pc on 2017/7/23.
 */

public class CreateRoomToServerPost {
    private int userid;//创建用户id
    private String pushurl;//推流地址
    private String tokenurl;//推流地址
    private String rongid;///聊天房间id===直播房间id
    private String playingname;//房间名字
    private String introduce;////房间介绍

    public CreateRoomToServerPost(int userid, String pushurl, String
            tokenurl, String rongid, String playingname, String introduce) {
        this.userid = userid;
        this.pushurl = pushurl;
        this.tokenurl = tokenurl;
        this.rongid = rongid;
        this.playingname = playingname;
        this.introduce = introduce;
    }

    public int getUserid() {
        return userid;
    }

    public String getPushurl() {
        return pushurl;
    }

    public String getTokenurl() {
        return tokenurl;
    }

    public String getRongid() {
        return rongid;
    }

    public String getPlayingname() {
        return playingname;
    }

    public String getIntroduce() {
        return introduce;
    }
}
