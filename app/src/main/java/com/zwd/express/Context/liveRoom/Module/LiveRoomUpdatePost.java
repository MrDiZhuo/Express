package com.zwd.express.Context.liveRoom.Module;

/**
 * Created by asus-pc on 2017/7/24.
 */

public class LiveRoomUpdatePost {
    private int id;///房间ID
    private String picurl;///直播封面

    public LiveRoomUpdatePost(int id, String picurl) {
        this.id = id;
        this.picurl = picurl;
    }

    public int getId() {
        return id;
    }

    public String getPicurl() {
        return picurl;
    }
}
