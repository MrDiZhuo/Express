package com.zwd.express.Context.liveList.Module;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus-pc on 2017/7/21.
 */

public class RoomPublishGet {
    @SerializedName("str")
    private String publishUrl;

    public RoomPublishGet(String publishUrl) {
        this.publishUrl = publishUrl;
    }

    public String getPublishUrl() {
        return publishUrl;
    }
}
