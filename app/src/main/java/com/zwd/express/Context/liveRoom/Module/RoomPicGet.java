package com.zwd.express.Context.liveRoom.Module;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus-pc on 2017/7/21.
 */

public class RoomPicGet {
    @SerializedName("targetUrl")
    private String publishUrl;

    public RoomPicGet(String publishUrl) {
        this.publishUrl = publishUrl;
    }

    public String getPublishUrl() {
        return publishUrl;
    }
}
