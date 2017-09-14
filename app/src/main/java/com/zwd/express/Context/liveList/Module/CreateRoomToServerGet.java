package com.zwd.express.Context.liveList.Module;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus-pc on 2017/7/23.
 */

public class CreateRoomToServerGet {
    @SerializedName("id")
    private int id;//创建用户id

    public int getId() {
        return id;
    }
}
