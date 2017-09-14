package com.zwd.express.Context.liveRoom.Module;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus-pc on 2017/7/24.
 */

public class LiveRoomUpdateGet {
    @SerializedName("cou")
    private int cou;//房间人数

    public LiveRoomUpdateGet(int cou) {
        this.cou = cou;
    }

    public int getCou() {
        return cou;
    }
}
